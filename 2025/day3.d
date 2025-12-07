import std.bigint;
import std.algorithm;
import std.array;
import std.stdio;
import std.format;
import std.typecons;
import std.string;
import std.math;

enum INPUT_FILE_PATH = "input/day3.txt";

void main() {
    part1();
    part2();
}

void part1() {
    File inputFile = File(INPUT_FILE_PATH, "r");
    long total = 0;
    foreach (string line; inputFile.lines()) {
        dchar tensChar = maxElement(line.strip()[0 .. $ - 1]);
        long tensCharIndex = countUntil(line.strip()[0 .. $ - 1], tensChar);
        dchar onesChar = maxElement(line.strip()[tensCharIndex + 1 .. $]);
        total += (tensChar - '0') * 10 + onesChar - '0';
    }
    writefln("part1 : %d", total);
}

void part2() {
    enum BATTERY_SIZE = 12;
    File inputFile = File(INPUT_FILE_PATH, "r");
    BigInt total = 0;
    foreach (string line; inputFile.lines()) {
        dchar[] chars = [];
        long lastLargestCharIndex = 0;
        for (int i = BATTERY_SIZE; i > 0; i--) {
            debug writefln("i = %d, lastLargestCharIndex = %d, searching for largest in [%(%c, %)]",
                i,
                lastLargestCharIndex,
                line.strip()[lastLargestCharIndex .. $ - (i - 1)]
            );
            dchar thisChar = maxElement(line.strip()[lastLargestCharIndex .. $ - (i - 1)]);
            lastLargestCharIndex = lastLargestCharIndex + 1 + countUntil(line.strip()[lastLargestCharIndex .. $ - (i - 1)], thisChar);
            chars ~= thisChar;
            debug writefln("saving char '%c' at pos %d", thisChar, lastLargestCharIndex);
        }
        BigInt lineTotal = 0;
        foreach (c; chars) {
            lineTotal = lineTotal * 10 + (c - '0');
        }
        total += lineTotal;
        debug writeln;
    }
    writefln("part2 : %d", total);
}
