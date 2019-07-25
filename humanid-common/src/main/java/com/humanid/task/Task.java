package com.humanid.task;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public abstract class Task<TResult> {

    public Task() { }

    public abstract boolean isSuccessful();

    @Nullable
    public abstract TResult getResult();

    @Nullable
    public abstract Exception getException();

    @NonNull
    public abstract Task<TResult> addOnSuccessListener(@NonNull OnSuccessListener<? super TResult> var1);

    public abstract void removeOnSuccessListener(@NonNull OnSuccessListener<? super TResult> var1);

    @NonNull
    public abstract Task<TResult> addOnFailureListener(@NonNull OnFailureListener var1);

    public abstract void removeFailureListener(@NonNull OnFailureListener var1);
}
