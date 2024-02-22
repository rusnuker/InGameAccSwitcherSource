//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "F:\cocaine\mcpbot_rip"!

//Decompiled by Procyon!

package ru.vidtu.ias;

import org.jetbrains.annotations.*;
import java.util.*;

public class Expression
{
    private final String expression;
    private int position;
    
    public Expression(final String expression) {
        this.expression = expression.replaceAll("\\s+", "");
    }
    
    @Contract(pure = true)
    private char current() {
        return (this.position >= this.expression.length()) ? '?' : this.expression.charAt(this.position);
    }
    
    public double parse() {
        return this.parse(0);
    }
    
    private double parse(final int returnFlag) {
        try {
            if (returnFlag == 2) {
                if (this.current() == '-') {
                    ++this.position;
                    return -this.parse(2);
                }
                if (this.current() == '(') {
                    ++this.position;
                    double x = this.parse(0);
                    if (this.current() == ')') {
                        ++this.position;
                    }
                    if (this.current() == '^') {
                        ++this.position;
                        x = Math.pow(x, this.parse(2));
                    }
                    return x;
                }
                final int begin = this.position;
                while (this.current() == '.' || Character.isDigit(this.current())) {
                    ++this.position;
                }
                double x = Double.parseDouble(this.expression.substring(begin, this.position));
                if (this.current() == '^') {
                    ++this.position;
                    x = Math.pow(x, this.parse(2));
                }
                return x;
            }
            else {
                double x = this.parse(2);
                while (true) {
                    if (this.current() == '*') {
                        ++this.position;
                        x *= this.parse(2);
                    }
                    else {
                        if (this.current() != '/') {
                            break;
                        }
                        ++this.position;
                        x /= this.parse(2);
                    }
                }
                if (returnFlag == 1) {
                    return x;
                }
                while (true) {
                    if (this.current() == '+') {
                        ++this.position;
                        x += this.parse(1);
                    }
                    else {
                        if (this.current() != '-') {
                            break;
                        }
                        ++this.position;
                        x -= this.parse(1);
                    }
                }
                return x;
            }
        }
        catch (Throwable t) {
            throw new IllegalArgumentException("Sorry, but we're unable to calculate " + this.expression + " at pos " + this.position, t);
        }
    }
    
    @Contract(pure = true)
    public static double parseWidthHeight(final String expression, final int width, final int height) throws IllegalArgumentException {
        return new Expression(expression.toLowerCase(Locale.ROOT).replace("w", String.valueOf(width)).replace("h", String.valueOf(height))).parse();
    }
}
