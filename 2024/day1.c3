import std::io;
import std::math;
import std::collections::list;
import std::collections::map;
import std::sort;

import lex;

def IntList    = List(<int>);
def IntIntMap  = HashMap(<int, int>);

fn void main() {
    File inputFile = file::open("input/day1.txt", "r")!!;
    defer (void)inputFile.close();
    File* stream = &inputFile;

    IntList left;  left.new_init();
    IntList right; right.new_init();

    while (try line = io::treadline(stream)) {
        StringList splitLine = lex::splitSpaces(line);
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
