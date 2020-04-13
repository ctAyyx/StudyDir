package com.ct.dagger.vo

import javax.inject.Inject

/**
 * 使用Inject注解标记当前类是可以注入的
 */
data class User @Inject constructor(
    val firstName: String = "",
    val lastName: String = "",
    val age: Int = 0
)