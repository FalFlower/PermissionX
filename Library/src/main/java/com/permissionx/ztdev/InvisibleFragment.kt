package com.permissionx.ztdev

import android.content.pm.PackageManager
import androidx.fragment.app.Fragment

//typealias关键字可以用于给任意类型指定一个别名
typealias PermissionCallback=(Boolean,List<String>)->Unit

class InvisibleFragment :Fragment(){
    private var callback:PermissionCallback?=null

    //vararg 可变长度遍参数列表
    fun requestNow(cb:PermissionCallback,vararg permissions:String){
        callback=cb
        requestPermissions(permissions,1)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode==1){
            val deniedList=ArrayList<String>()//用来存放被用户拒绝的权限
            for ((index,result) in grantResults.withIndex()){
                if (result!=PackageManager.PERMISSION_GRANTED){
                    deniedList.add(permissions[index])
                }
            }
            val allGranted=deniedList.isEmpty()
            callback?.let {
                it(allGranted,deniedList)
            }
        }
    }

}