import std::io;
import std::math;
import std::collections::list;
import std::collections::map;
import std::sort;

def IntList    = List(<int>);
def StringList = List(<String>);
def IntIntMap  = HashMap(<int, int>);

fn void main() {
    File inputFile = file::open("input/day1.txt", "r")!!;
    defer (void)inputFile.close();
    File* stream = &inputFile;

    IntList left;  left.new_init();
    IntList right; right.new_init();

    while (try line = io::treadline(stream)) {
        StringList splitLine = splitSpaces(line);
        assert(splitLine.len() == 2);
        left.push(splitLine[0].to_int()!!);
        right.push(splitLine[1].to_int()!!);
    }

    quicksort(left);
    quicksort(right);

    int sumOfDiffs = 0;
    for (int i = 0; i < left.len(); i++) {
        sumOfDiffs += math::abs(left[i] - right[i]);
    }

    io::printfn("Sum of differences : %d", sumOfDiffs);

    IntIntMap rightListNumberCounts; rightListNumberCounts.new_init();
    foreach (int n : right) {
        if (!rightListNumberCounts.has_key(n)) {
            rightListNumberCounts[n] = 1;
        } else {
            rightListNumberCounts[n] = rightListNumberCounts[n]!! + 1;
        }
    }

    int sumOfSimilarities = 0;
    foreach (int n : left) {
        sumOfSimilarities += n * (rightListNumberCounts[n] ?? 0);
    }

    io::printfn("Sum of similarities : %d", sumOfSimilarities);
}

enum LexState {
    SPACE,
    CHAR,
}

fn StringList splitSpaces(String s) {
    StringList result;
    result.new_init();

    LexState state = CHAR;
    usz wordStart = 0;
    if (s[0] == ' ') state = SPACE;

    for (usz i = 1; i < s.len; i++) {
        if (state == CHAR) {
            if (s[i] == ' ') {
                state = SPACE;
                result.push(s[wordStart..i - 1]);
            }
        }

        if (state == SPACE) {
            if (s[i] != ' ') {
                state = CHAR;
                wordStart = i;
            }
        }
    }

    if (state == CHAR) {
        result.push(s[wordStart .. s.len - 1]);
    }

    return result;
}
