package com.abnndn.day2;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Entity {
    public int min;
    public int max;
    public char letter;
    public String word;
}
