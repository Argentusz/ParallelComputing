import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class Matrix (
    val values: Array<IntArray>
) {
    fun print() {
        for(i in 0 until 3) {
            for(j in 0 until 3) {
                print(this.values[i][j].toString() + " ")
            }
            println()
        }
    }
    private fun ij(m1: IntArray, m2: IntArray) : Int {
        var res = 0
        for (k in 0 until 3) {
            res += m1[k] * m2[k]
        }
        return res
    }
    fun getRow(i: Int) : IntArray {
        return this.values[i]
    }
    fun getColumn(i: Int) : IntArray {
        val res = IntArray(3)
        for (k in 0 until 3) {
            res[k] = this.values[k][i]
        }
        return res
    }
    fun set(i: Int, j: Int, value: Int) {
        this.values[i][j] = value
    }
    fun multiply(on: Matrix) : Matrix {
        val threadPool: ExecutorService = Executors.newFixedThreadPool(9)
        val res = Matrix(arrayOf(IntArray(3), IntArray(3), IntArray(3)))
        for(i in 0 until 3) {
            for(j in 0 until 3) {
                threadPool.submit {
                    res.set(i, j, ij(this.getRow(i), on.getColumn(j)))
                }
            }
        }
        return res
    }
}
fun main() {
    val m1 = Matrix(arrayOf(
        intArrayOf(1,2,3),
        intArrayOf(4,5,6),
        intArrayOf(7,8,9)
    ))
    val m2 = Matrix(arrayOf(
        intArrayOf(1,1,1),
        intArrayOf(2,0,4),
        intArrayOf(5,4,3)
    ))
    m1.multiply(m2).print()
}