import std.algorithm;
import std.array;
import std.stdio;
import std.format;
import std.typecons;
import std.string;
import std.math;

enum INPUT_FILE_PATH = "input/day2.txt";

void main() {
    part1();
    part2();
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

void part2() {
    File inputFile = File(INPUT_FILE_PATH, "r");
    string line = inputFile.readln().strip();
    long[] invalidIDs = [];
    foreach (string interval; line.split(",")) {
        auto bounds = interval.formattedRead!("%u-%u", long, long);
        int[] differentDivisors = [];
        long low = bounds[0];
        long high = bounds[1];

        for (int i = digitCount(low); i <= digitCount(high); i++) {
            foreach (divisor; i.divisors) {
                if (find(differentDivisors, divisor).empty) differentDivisors ~= divisor;
            }
        }
        debug writefln("%,d - %,d : [%(%d, %)]", low, high, differentDivisors);

        foreach (divisor; differentDivisors) {
            debug writefln("Calling aggregateResults with divisor %d", divisor);
            aggregateResults(invalidIDs, bounds[0], bounds[1], divisor);
        }
    }
    long total = sum(invalidIDs);
    writefln("part2 : %d", total);
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

/// divisor : how many parts do we want to divide numbers into ?
///           if it is 3, then 121212 will be added to the result, because we
///           can divide it in 3 parts, all equal to `12`
void aggregateResults(ref long[] invalidIDs, long low, long high, int divisor) {
    if (    digitCount(low) == digitCount(high)
        && (digitCount(low) % divisor) != 0
    ) {
        return;
    }

    if (divisor == 1) return; // We can't divide in 1 part

    long cursor = void;
    if (digitCount(low) % divisor == 0) {
        cursor = low / (10 ^^ (digitCount(low) - digitCount(low) / divisor));
    } else {
        cursor = 10 ^^ (digitCount(low) / divisor);
    }
    debug writefln("%,d - %,d : start cursor at %,d", low, high, cursor);

    long current = void;
    do {
        current = repeatInteger(cursor, divisor);

        if (current >= low && current <= high) {
            if (!invalidIDs.find(current).empty) goto next;
            invalidIDs ~= current;
            debug writefln("cursor | current : %,d | %,d", cursor, current);
        }
next:
        cursor++;
    } while (current <= high);
}

int[] divisors(int n) {
    int[] result;
    int squareRootN = cast(int)sqrt(cast(float)n);
    for (int i = 1; i <= squareRootN; i++) {
        if (n % i == 0) {
            result ~= i;
        }
    }

    foreach (k; result) {
        if (k ^^ 2 == n) continue; // Prevent putting the square root of n twice in the list
        result ~= n / k;
    }

    debug writefln("Divisors of %d : %(%d, %)", n, result);
    return sort(result).array;
}

unittest {
    assert(divisors(2) == [1, 2]);
    assert(divisors(9) == [1, 3, 9]);
    assert(divisors(12) == [1, 2, 3, 4, 6, 12]);
    assert(divisors(625) == [1, 5, 25, 125, 625]);
}

long repeatInteger(long n, int times) {
    long result = n;
    for (int i = 0; i < times - 1; i++) {
        result *= 10 ^^ digitCount(n);
        result += n;
    }
    return result;
}

unittest {
    assert(repeatInteger(2, 2) == 22);
    assert(repeatInteger(123, 4) == 123_123_123_123);
    assert(repeatInteger(9990, 3) == 9990_9990_9990);
}
