module lex;

import std::collections;

def StringList = List(<String>);

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
