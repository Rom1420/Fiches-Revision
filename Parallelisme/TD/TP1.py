from numba import cuda
import numba as nb
import sys
from timeit import default_timer as timer
import numpy as np


def firstKernel():
    # Chaque thread imprime "hello"
    print("hello")


# Appel du kernel avec 1 bloc et 4 threads
firstKernel[1, 4]()  # 1 bloc, 4 threads


@cuda.jit
def coordinates1D():
    local_id = cuda.threadIdx.x
    global_id = cuda.blockIdx.x * cuda.blockDim.x + cuda.threadIdx.x
    print(f"Local ID: {local_id}, Global ID: {global_id}")


@cuda.jit
def coordinates2D():
    local_x = cuda.threadIdx.x
    local_y = cuda.threadIdx.y
    global_x = cuda.blockIdx.x * cuda.blockDim.x + local_x
    global_y = cuda.blockIdx.y * cuda.blockDim.y + local_y
    print(f"Local ID: ({local_x}, {local_y}), Global ID: ({global_x}, {global_y})")


@cuda.jit
def computeInt(a,n) :
    r = 1
    for i in range(1, n):
        r*=a

@cuda.jit
def computeFloat(a,n) :
    r = 1.0
    for i in range(1, n):
        r*=a


def benchInt(runs):
    threadsPerBlock = 1024
    blocksPerGrid = 10
    result = []
    a = 2
    n = 10000
    for i in range(runs):
        start = timer()
        computeInt[blocksPerGrid,blocksPerGrid](a,n)
        cuda.synchronize()
        dt = timer() - start
        print(" ", dt, " s")
        result.append(dt)
    print("Average Int :", threadsPerBlock, np.average(result[1:]))
    nops = (n*threadsPerBlock*blocksPerGrid)/np.average(result[1:])
    print("Nb operations/s : ", '{:.2e}'.format(nops))

def benchFloat(runs):
    threadsPerBlock = 1024
    blocksPerGrid = 1
    result = []
    a = np.float32(2.0)
    n = 10000
    for i in range(runs):
        start = timer()
        computeFloat[blocksPerGrid, threadsPerBlock](a,n)
        cuda.synchronize()
        dt = timer() - start
        print(" ", dt, " s")
        result.append(dt)
    print("Average Float32 :", threadsPerBlock, np.average(result[1:]))
    nops = (n*threadsPerBlock*blocksPerGrid)/np.average(result[1:])
    print("Nb operations/s : ", '{:.2e}'.format(nops))


if __name__ == '__main__':
    benchInt(10)
    benchFloat(10)
    print(computeInt.inspect_llvm())
    print(computeFloat.inspect_llvm())
