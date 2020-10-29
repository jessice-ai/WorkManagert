package com.example.sunmultipleworkmanager

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.work.*
import kotlinx.android.synthetic.main.activity_main.*

const val INPUT_DATA_KEY = "input_data_key"
const val WORK_A_NAME = "Work A"
const val WORK_B_NAME = "Work B"
const val OUT_DATA_KEY = "out_data_key"
const val OUT_TABLE = "out_table"
const val OUT_TABLE_KEY_A = "key_A"
const val OUT_TABLE_KEY_B = "key_B"

/**
 * 更新前段，第二步：
 * 集成 SharedPreferences.OnSharedPreferenceChangeListener 目的，侦听后端任务发生变化，更新前段
 * 是一个抽象类，需要实现抽象类
 */
class MainActivity : AppCompatActivity(),SharedPreferences.OnSharedPreferenceChangeListener {
    /**
     * WorkManager  第一步
     * 获取一个WorkManager的引用
     */
    private val sunworkManager = WorkManager.getInstance(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        updateView()
        //注册一个侦听器
        val sp = getSharedPreferences(OUT_TABLE,Context.MODE_PRIVATE)
        sp.registerOnSharedPreferenceChangeListener(this)

        button1.setOnClickListener {
            /**
             * WorkManager  第二步
             * 创建第一个工作请求
             * 请求分为两种，一种是单次请求，二种是周期性请求
             */
            val sunworkRequest1 : OneTimeWorkRequest = OneTimeWorkRequestBuilder<SunMultipleWork>()
                    .setInputData(workDataOf(INPUT_DATA_KEY to WORK_A_NAME)) //这里的 workDataOf 参数key value 用to 连接，向任务里面传递参数值
                    .build()  //单次请求
            /**
             * WorkManager  第三步
             * 创建第二个工作请求
             * 请求分为两种，一种是单次请求，二种是周期性请求
             */
            val sunworkRequest2 : OneTimeWorkRequest = OneTimeWorkRequestBuilder<SunMultipleWork>()
                    .setInputData(workDataOf(INPUT_DATA_KEY to WORK_B_NAME)) //这里的 workDataOf 参数key value 用to 连接，向任务里面传递参数值
                    .build()  //单次请求
            /**
             * WorkManager  第四步
             * 实现，执行完任务一，再执行任务二
             */
            sunworkManager.beginWith(sunworkRequest1)
                    .then(sunworkRequest2)
                    .enqueue()

        }
    }
    private fun updateView(){
        /**
         * 从 getSharedPreferences 中拿回数据，更新界面
         */
        val sp:SharedPreferences = getSharedPreferences(OUT_TABLE,Context.MODE_PRIVATE)
        println("Jessice：更新界面中")
        textView1.text = sp.getInt(OUT_TABLE_KEY_A,0).toString()
        textView2.text = sp.getInt(OUT_TABLE_KEY_B,0).toString()
    }
    /**
     * 更新前段，第三步：
     * 集成 SharedPreferences.OnSharedPreferenceChangeListener 目的，侦听后端任务发生变化，更新前段
     */
    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        //后台任务状态发生变化，也就是执行完成后，更新前段界面
        println("Jessice：检测到状态发生变化")
        updateView()
    }

}