package com.ct.study.test;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * @ClassName : User
 * @CreateDate : 2020/4/15 12:46
 * @Author : CT
 * @Description :
 */
@Entity
public class User {

    @PrimaryKey
    @NonNull
    public String id;
    public String name;
    public int age;
}
