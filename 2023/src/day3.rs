use std::fs;

pub fn launch() {
    let file_path = "input/day3.txt";
    let file_content =
        fs::read_to_string(file_path).expect("Should have been able to read the file");

    part1(file_content.as_str());
    // part2(file_content.as_str());
}

enum Cell {
    Number(i32),
    AnyNumber,
    Symbol(Symbol),
    Blank,
}

enum Symbol {
    Char(char),
    Any,
}

impl PartialEq for Cell {
    fn eq(&self, other: &Self) -> bool {
        match (self, other) {
            (Self::Number(n1), Self::Number(n2)) => n1 == n2,
            (Self::Number(_), Self::AnyNumber) => true,
            (Self::AnyNumber, Self::Number(_)) => true,
            (Self::AnyNumber, Self::AnyNumber) => true,

            (Self::Symbol(Symbol::Char(c1)), Self::Symbol(Symbol::Char(c2))) => c1 == c2,
            (Self::Symbol(Symbol::Any), Self::Symbol(Symbol::Char(_))) => true,
            (Self::Symbol(Symbol::Char(_)), Self::Symbol(Symbol::Any)) => true,
            (Self::Symbol(Symbol::Any), Self::Symbol(Symbol::Any)) => true,

            (Self::Blank, Self::Blank) => true,

            _ => false,
        }
    }
}

fn part1(file_content: &str) {
    let mut digit_map: Vec<Vec<Cell>> = Vec::new();

    for (line_index, line) in file_content.lines().enumerate() {
        let mut new_line = Vec::new();
        for (character_index, character) in line.chars().enumerate() {
            let content: Cell = match character {
                '0' | '1' | '2' | '3' | '4' | '5' | '6' | '7' | '8' | '9' => {
                    Cell::Number(character.to_digit(10).unwrap() as i32)
                }
                '.' => Cell::Blank,
                _ => Cell::Symbol(Symbol::Char(character)),
            };
            new_line.push(content);
        }
        digit_map.push(new_line);
    }

    for line in digit_map.as_mut_slice() {
        for i in 0..(line.len() - 1) {
            match line[i] {
                Cell::Number(number) => match line[i + 1] {
                    Cell::Number(next_number) => {
                        line[i] = Cell::Number(number * 10 + next_number);
                        line[i + 1] = Cell::Number(number * 10 + next_number);
                    }
                    _ => (),
                },
                _ => (),
            }
        }
    }

    let mut sum = 0;

    for (line_index, line) in digit_map.as_slice().iter().enumerate() {
        for (cell_index, cell) in line.iter().enumerate() {
            match cell {
                Cell::Symbol(Symbol::Char(s)) => {
                    let found_neighbors = neighbors_search(
                        digit_map.as_slice(),
                        Cell::AnyNumber,
                        line_index,
                        cell_index,
                    ) {
                        sum += found_neighbors;
                        println!(
                            "Detected part number at line {line_index} col {cell_index} : {number}"
                        )
                    }
                }
                _ => (),
            }
        }
    }
    println!("part 1 : {sum}")
}

fn neighbors_search(map: &[Vec<Cell>], symbol: Symbol, y: usize, x: usize) -> Vec<i32> {
    let mut neighbors: Vec<i32> = Vec::new();

    let min_y = if y == 0 { 0 } else { y - 1 };
    let max_y = if y == map.len() - 1 {
        map.len() - 1
    } else {
        y + 1
    };

    for y in min_y..=max_y {
        let min_x = if x == 0 { 0 } else { x - 1 };
        let max_x = if x == map[y].len() - 1 {
            map.len() - 1
        } else {
            x + 1
        };

        for x in min_x..=max_x {
            if map[y][x] == Cell::Symbol(symbol) {
                neighbors.push(value)
            }
        }
    }
    false
}
