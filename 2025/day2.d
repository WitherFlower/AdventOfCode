import std.stdio;
import std.format;
import std.typecons;
import std.string;
import std.math;

enum INPUT_FILE_PATH = "input/day2.txt";

void main() {
    part1();
    /* part2(); */
}

void part1() {
    File inputFile = File(INPUT_FILE_PATH, "r");
    string line = inputFile.readln().strip();
    long total = 0;
    foreach (string interval; line.split(",")) {
        auto bounds = interval.formattedRead!("%u-%u", long, long);
        aggregateResults(total, bounds[0], bounds[1]);
    }
    writefln("part1 : %d", total);
}

int digitCount(long num) {
    if (num == 0) return 1;
    return cast(int)log10(cast(double)num) + 1;
}

unittest {
    assert(digitCount(10)  == 2);
    assert(digitCount(99)  == 2);
    assert(digitCount(100) == 3);
}

void aggregateResults(ref long total, long low, long high) {
    if (    digitCount(low) == digitCount(high)
        && (digitCount(low) % 2) != 0
    ) {
        return;
    }

    long cursor = void;
    if (digitCount(low) % 2 == 0) {
        cursor = low / (10 ^^ (digitCount(low) / 2));
    } else {
        cursor = 10 ^^ (digitCount(low) / 2);
    }
    debug writefln("%,d - %,d : start cursor at %,d", low, high, cursor);

    long current = void;
    do {
        current = cursor * 10 ^^ (digitCount(cursor))
                + cursor;
        debug writefln("cursor, current : %,d, %,d", cursor, current);

        if (current >= low && current <= high) {
            total += current;
        }

        cursor++;
    } while (current <= high);
}
