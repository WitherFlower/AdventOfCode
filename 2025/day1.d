import std.stdio;
import std.format;
import std.typecons;
import std.string;

enum INPUT_FILE_PATH = "input/day1.txt";

void main() {
    part1();
    part2();
}

void part1() {
    int dialPosition = 50;
    int zeroes = 0;
    File inputFile = File(INPUT_FILE_PATH, "r");
    foreach (string line; inputFile.lines()) {
        line = line.strip();
        if (line.length <= 1) continue;
        auto data = line.formattedRead!("%c%d", char, int);
        final switch (data[0]) {
            case 'L': dialPosition -= data[1]; break;
            case 'R': dialPosition += data[1]; break;
        }
        dialPosition %= 100;
        if (dialPosition == 0) zeroes++;
    }
    writeln("Part 1 : ", zeroes);
}

void part2() {
    int dialPosition = 50;
    int zeroes = 0;
    File inputFile = File(INPUT_FILE_PATH, "r");
    foreach (string line; inputFile.lines()) {
        line = line.strip();
        if (line.length <= 1) continue;
        debug writeln("Dial is at ", dialPosition, " doing ", line);
        bool wasAtZero = (dialPosition == 0);
        auto data = line.formattedRead!("%c%d", char, int);

        int moves = data[1];
        zeroes += moves / 100;
        moves = moves % 100;
        if (moves == 0) continue;

        assert (dialPosition >= 0 && dialPosition <= 99);

        final switch (data[0]) {
            case 'L':
                dialPosition -= moves;
                break;
            case 'R':
                dialPosition += moves;
                break;
        }

        assert (dialPosition >= -99 && dialPosition <= 198);

        // Landed on zero
        if (dialPosition == 0) {
            zeroes++;
        }

        // Went from zero to negative
        if (wasAtZero && dialPosition < 0) {
            dialPosition += 100;
        }

        // Went from positive to negative
        if (!wasAtZero && dialPosition < 0) {
            dialPosition += 100; zeroes++;
        }

        // Went over 100
        if (dialPosition >= 100) {
            dialPosition -= 100; zeroes++;
        }

        debug writeln("Position : ", dialPosition, ", zeroes: ", zeroes);
    }
    writeln("Part 2 : ", zeroes);
}
