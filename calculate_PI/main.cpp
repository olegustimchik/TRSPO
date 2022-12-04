#include <iostream>
#include <math.h>
#include <time.h>
#include <mpi.h>
#include <vector>
#include <chrono>
#include <string>



const int SIZE = 10000000;
const double RADIUS = 1.0;
double rundReal(double low, double high);
double doComputation(std::vector<double> array);
void output(std::vector<double> array);


int main(int argc, char** argv) {

    srand(time(NULL));

    int size, rank;
    int tag = 0;
    int n;
    std::string new_message;
    int length = 0;

    MPI_Status status;

    MPI_Init(&argc, &argv);
    MPI_Comm_size(MPI_COMM_WORLD, &size);
    MPI_Comm_rank(MPI_COMM_WORLD, &rank);



    if (rank == 0) {
        std::vector<double> message;
        int partVect = 2 * floor(SIZE / (size));

        for (int i = 1; i < size; i++) {
            length = partVect * i;
            for (int j = 0; j < partVect; j++)
                message.push_back(rundReal(0.0, RADIUS));

            MPI_Send(&length, 1, MPI_INT, i, tag, MPI_COMM_WORLD);
            MPI_Send(message.data(), length, MPI_DOUBLE, i, tag, MPI_COMM_WORLD);

            int messageLen;

            MPI_Recv(&messageLen, 1, MPI_INT, i, tag, MPI_COMM_WORLD, &status);

            char* buf = new char[messageLen];

            MPI_Recv(buf, messageLen, MPI_CHAR, i, tag, MPI_COMM_WORLD, &status);

            new_message = buf;
            std::cout << new_message << std::endl;
        }
    }
    else {
        double start = MPI_Wtime();
        int vector_size;
        MPI_Recv(&vector_size, 1, MPI_INT, 0, tag, MPI_COMM_WORLD, &status);

        std::vector<double> message(vector_size);

        MPI_Recv(message.data(), vector_size, MPI_DOUBLE, 0, tag, MPI_COMM_WORLD, &status);

        double pi = doComputation(message);


        double dura = MPI_Wtime() - start;
        new_message = "Procces " + std::to_string(rank) + ": pi = " + std::to_string(pi) + " time " + std::to_string(dura);
        int len = new_message.length();
        MPI_Send(&len, 1, MPI_INT, 0, tag, MPI_COMM_WORLD);
        MPI_Send(new_message.c_str(), new_message.length(), MPI_CHAR, 0, tag, MPI_COMM_WORLD);
    }

    MPI_Finalize();

}


double rundReal(double low, double high) {
    return low + static_cast <double> (rand()) / (static_cast <double> (RAND_MAX / (high - low)));
}

double doComputation(std::vector<double> array) {
    int circul = 0;
    for (int i = 0; i < array.size(); i += 2) {
        if (array[i] * array[i] + array[i + 1] * array[i + 1] < RADIUS * RADIUS) {
            circul++;
        }

    }

    return  4.0 * (double)(circul) / (double)array.size() * 2.0;
}

void output(std::vector<double> array) {
    for (int i = 0; i < array.size(); i++) {
        std::cout << array[i] << " ";
    }
    std::cout << std::endl;
}