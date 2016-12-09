package com.practice.event_car;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @作者:XJY
 * @创建日期: 2016/12/7 20:00
 * @描述:${TODO}
 * @更新者:${Author}$
 * @更新时间:${Date}$
 * @更新描述:${TODO}
 */
public class Student implements Parcelable {
    public String name;
    public int age;
    public boolean isMan;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeInt(this.age);
        dest.writeByte(isMan ? (byte) 1 : (byte) 0);
    }

    public Student() {
    }

    private Student(Parcel in) {
        this.name = in.readString();
        this.age = in.readInt();
        this.isMan = in.readByte() != 0;
    }

    public static final Parcelable.Creator<Student> CREATOR = new Parcelable.Creator<Student>() {
        public Student createFromParcel(Parcel source) {
            return new Student(source);
        }

        public Student[] newArray(int size) {
            return new Student[size];
        }
    };
}
