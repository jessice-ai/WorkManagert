package com.example.sunmultipleworkmanager

import android.content.Context
import android.content.SharedPreferences
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf

class SunMultipleWork(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
    override fun doWork(): Result {
        //接收传递过来的数据
        val name : String? = inputData.getString(INPUT_DATA_KEY)
        println("Jessice：任务执行开始 $name ")
        /**
         * 更新前段，第一步：
         * Context.MODE_PRIVATE 是使用上下文私有模式
         * 根据Context获取getSharedPreferences对象
         * 利用edit()方法获取Editor对象
         * 通过Editor对象存储key-value键值对数据
         * getSharedPreferences 两个参数，第一个参数名称，第二个参数打开方式
         * 把 Context.MODE_PRIVATE 当作一个表，表名为：OUT_TABLE，然后可以通过这个对象操作
         * 保存 OUT_A_KEY 的值，到变量
         * sun_sp 相当于一个表，不在本地生成文件
         */
        val sun_sp:SharedPreferences = applicationContext.getSharedPreferences(OUT_TABLE,Context.MODE_PRIVATE)

        /**
         * 利用edit()方法获取Editor对象
         */
        var editor = sun_sp.edit()

        var number_A:Int = sun_sp.getInt(OUT_TABLE_KEY_A,0)
        var number_B:Int = sun_sp.getInt(OUT_TABLE_KEY_B,0)
        println("Jessice：number_A： $number_A ")
        println("Jessice：number_B： $number_B ")
        editor.putString("STRING_KEY", "string");
        /**
         * 更新字段值
         */
        editor.putInt(OUT_TABLE_KEY_A,++number_A)
        editor.putInt(OUT_TABLE_KEY_B,++number_B)

        /**
         * 这里有多种数据类型
         */
        editor.putString("STRING_KEY", "string");
        editor.putInt("INT_KEY", 0);
        editor.putBoolean("BOOLEAN_KEY", true);
        //提交
        editor.commit()
        /**
         * 后台任务代码放置区
         */

        Thread.sleep(3000)
        println("Jessice：任务执行结束 $name ")
        //返回一个成功提示，并传递数据出去
        return Result.success()
    }
}