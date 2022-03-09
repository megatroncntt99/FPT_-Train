
package com.fpttelecom.train.android.base

  open class Test {
    private fun test1() {

    }
     fun test2() {

    }
    protected  fun test3() {

    }
      internal fun test4(){

      }
}
class Test12L:Test(){
    fun a(){
        test3()
        test4()
        Test().test4()
    }

}

