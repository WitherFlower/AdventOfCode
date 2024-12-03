import std::io;
import std::collections;
import std::math;

import lex;

def IntList = List(<int>);

fn void main() {
    File inputFile = file::open("input/day2.txt", "r")!!;
    defer (void)inputFile.close();
    File* stream = &inputFile;

    uint safeCount = 0;
    uint dampenedSafeCount = 0;

    while (try line = io::treadline(stream)) {
        StringList splitLine = lex::splitSpaces(line);

        IntList lineData;
        lineData.new_init(splitLine.len());

        foreach (String s : splitLine) {
            lineData.push(s.to_int()!!);
        }

        if (isSafe(lineData)) {
            safeCount++;
            dampenedSafeCount++;
        } else {
            for (int i = 0; i < lineData.len(); i++) {
                IntList simplifiedLineData;
                simplifiedLineData.new_init(lineData.len() - 1);
                foreach (index, value : lineData) {
                    if (index != i) {
                        simplifiedLineData.push(value);
                    }
                }
                if (isSafe(simplifiedLineData)) {
                    dampenedSafeCount++;
                    break;
                }
            }
        }

    }

    io::printfn("Safe Report Count : %d", safeCount);
    io::printfn("Safe Report Count w/ damp : %d", dampenedSafeCount);
}

fn bool isSafe(IntList lineData) {

    int startingSign = math::sign(lineData[1] - lineData[0]);

    for (usz i = 0; i < lineData.len() - 1; i++) {

        // Conditions are rules for invalid reports
        if (math::abs(lineData[i+1] - lineData[i]) >= 4) {
            return false;
        }

        if (lineData[i+1] - lineData[i] == 0) {
            return false;
        }

        if (math::sign(lineData[i+1] - lineData[i]) != startingSign) {
            return false;
        }
    }

    return true;
}
