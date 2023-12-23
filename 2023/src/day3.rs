use std::{fs, net};

pub fn launch() {
    let file_path = "input/day3.txt";
    let file_content =
        fs::read_to_string(file_path).expect("Should have been able to read the file");

    part1(file_content.as_str());
    part2(file_content.as_str());
}

struct PartNumber {
    value: i32,
    start_row: usize,
    start_col: usize,
    length: usize,
}

enum Cell {
    Symbol(char),
    Other,
}

enum ReadingState {
    Number(NumberReadingInfo),
    Other,
}

struct NumberReadingInfo {
    value: i32,
    start_col: usize,
    length: usize,
}

fn part1(file_content: &str) {
    let mut reading_state: ReadingState = ReadingState::Other;
    let mut numbers: Vec<PartNumber> = Vec::new();
    let mut symbol_map: Vec<Vec<Cell>> = Vec::new();

    for (line_index, line) in file_content.lines().enumerate() {
        let mut map_row: Vec<Cell> = Vec::new();

        for (character_index, character) in line.chars().enumerate() {
            process_character(
                character,
                &mut numbers,
                &mut map_row,
                &mut reading_state,
                line_index,
                character_index,
            )
        }
        end_number_reading(&mut reading_state, &mut numbers, line_index);
        symbol_map.push(map_row);
    }

    let row_count = file_content.lines().count();
    let col_count = file_content.lines().next().unwrap().len();

    let mut sum: i32 = 0;

    for number in numbers {
        for (row, col) in get_neighboring_cell_indexes(&number, row_count, col_count) {
            match symbol_map[row][col] {
                Cell::Symbol(s) => {
                    let value = number.value;
                    println!("Found symbol {s} next to number {value} at {row}:{col}");
                    sum += number.value;
                }
                Cell::Other => {}
            }
        }
    }

    println!("part 1 result : {sum}");
}

fn process_character(
    character: char,
    numbers: &mut Vec<PartNumber>,
    map_row: &mut Vec<Cell>,
    reading_state: &mut ReadingState,
    line_index: usize,
    character_index: usize,
) {
    match character {
        '0' | '1' | '2' | '3' | '4' | '5' | '6' | '7' | '8' | '9' => {
            process_digit(
                character.to_digit(10).unwrap() as i32,
                reading_state,
                character_index,
            );
            map_row.push(Cell::Other);
        }
        '.' => {
            end_number_reading(reading_state, numbers, line_index);
            map_row.push(Cell::Other)
        }
        symbol => {
            end_number_reading(reading_state, numbers, line_index);
            map_row.push(Cell::Symbol(symbol))
        }
    }
}

fn end_number_reading(reading_state: &mut ReadingState, numbers: &mut Vec<PartNumber>, line_index: usize) {
    match &reading_state {
        ReadingState::Number(n) => numbers.push(PartNumber {
            value: n.value,
            start_row: line_index,
            start_col: n.start_col,
            length: n.length,
        }),
        ReadingState::Other => {}
    };
    *reading_state = ReadingState::Other;
}

fn process_digit(digit: i32, reading_state: &mut ReadingState, character_index: usize) {
    // println!("Processing digit {digit} at index {character_index}");
    match reading_state {
        ReadingState::Number(n) => {
            *reading_state = ReadingState::Number(NumberReadingInfo {
                value: n.value * 10 + digit,
                start_col: n.start_col,
                length: n.length + 1,
            })
        }
        ReadingState::Other => {
            *reading_state = ReadingState::Number(NumberReadingInfo {
                value: digit,
                start_col: character_index,
                length: 1,
            })
        }
    }
}

fn get_neighboring_cell_indexes(
    number: &PartNumber,
    row_count: usize,
    col_count: usize,
) -> Vec<(usize, usize)> {
    let min_row = if number.start_row == 0 { 0 } else { number.start_row - 1};
    let max_row = (number.start_row + 1).min(row_count - 1);
    let min_col = if number.start_col == 0 { 0 } else { number.start_col - 1};
    let max_col = (number.start_col + number.length).min(col_count - 1);

    // Printing shenanigans
    // let value = number.value;
    // let row = number.start_row;
    // let col = number.start_col;
    // let len = number.length;
    // println!("Number {value} at {row}:{col}, length {len} has the following indexes : ");

    let mut indexes: Vec<(usize, usize)> = Vec::new();
    for row in min_row..=max_row {
        for col in min_col..=max_col {
            indexes.push((row, col));
            // println!("\t{row}:{col}");
        }
    }
    return indexes;
}

fn part2(file_content: &str) {}
