import std::io;
import std::collections;

enum State {
    INVALID,
    M,
    U,
    L,
    LPAR,
    FIRSTNUM,
    COMMA,
    SECONDNUM,
    D,
    O,
    N,
    APOSTROPHE,
    T,
    DO_LPAR,
    DONT_LPAR,
}

struct Parser {
    State state;

    DString firstNum;
    DString secondNum;
}

fn void Parser.new_init(Parser* self) {
    self.state = INVALID;
    self.firstNum.new_init();
    self.secondNum.new_init();
}

fn void Parser.free(Parser* self) {
    self.firstNum.free();
    self.secondNum.free();
}

fn void Parser.nextChar(Parser* self, char next, int* total, bool* enabled) {
    switch(self.state) {
        case INVALID:
            if (next == 'm') {
                self.state = M;
            }
            if (next == 'd') {
                self.state = D;
            }

        case M:
            if (next == 'u') {
                self.state = U;
            } else {
                self.state = INVALID;
            }

        case U:
            if (next == 'l') {
                self.state = L;
            } else {
                self.state = INVALID;
            }

        case L:
            if (next == '(') {
                self.state = LPAR;
            } else {
                self.state = INVALID;
            }

        case LPAR:
            if (next >= '0' && next <= '9') {
                self.state = FIRSTNUM;
                self.firstNum.clear();
                self.firstNum.append(next);
            } else {
                self.state = INVALID;
            }

        case FIRSTNUM:
            if (next >= '0' && next <= '9') {
                self.firstNum.append(next);
            } else if (next == ',') {
                self.state = COMMA;
            } else {
                self.state = INVALID;
            }

        case COMMA:
            if (next >= '0' && next <= '9') {
                self.state = SECONDNUM;
                self.secondNum.clear();
                self.secondNum.append(next);
            } else {
                self.state = INVALID;
            }

        case SECONDNUM:
            if (next >= '0' && next <= '9') {
                self.secondNum.append(next);
            } else if (next == ')') {
                self.state = INVALID;
                if (*enabled) {
                    *total += self.firstNum.str_view().to_int()!! * self.secondNum.str_view().to_int()!!;
                }
            } else {
                self.state = INVALID;
            }

        case D:
            if (next == 'o') {
                self.state = O;
            } else {
                self.state = INVALID;
            }

        case O:
            if (next == '(') {
                self.state = DO_LPAR;
            } else if (next == 'n') {
                self.state = N;
            } else {
                self.state = INVALID;
            }

        case DO_LPAR:
            if (next == ')') {
                *enabled = true;
            }
            self.state = INVALID;

        case N:
            if (next == '\'') {
                self.state = APOSTROPHE;
            } else {
                self.state = INVALID;
            }

        case APOSTROPHE:
            if (next == 't') {
                self.state = T;
            } else {
                self.state = INVALID;
            }

        case T:
            if (next == '(') {
                self.state = DONT_LPAR;
            } else {
                self.state = INVALID;
            }

        case DONT_LPAR:
            if (next == ')') {
                *enabled = false;
            }
            self.state = INVALID;

    }
}

fn void main() {
    File inputFile = file::open("input/day3.txt", "r")!!;
    defer (void)inputFile.close();

    Parser parser;
    parser.new_init();

    int total = 0;
    bool enabled = true;

    while (try c = inputFile.read_byte()) {
        parser.nextChar(c, &total, &enabled);
    }

    io::printfn("Total of mul operations: %d", total);
}
