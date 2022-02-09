package com.mymb.platform.api.support;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public abstract class APIResponse<T> {

    @NotNull
    private T data;

    private List<String> errors;
}
