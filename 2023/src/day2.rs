use std::fs;

pub fn launch() {
    let file_path = "input/day2.txt";
    let file_content =
        fs::read_to_string(file_path).expect("Should have been able to read the file");

    part1(file_content.as_str());
    part2(file_content.as_str());
}

enum Cube {
    Red(i32),
    Green(i32),
    Blue(i32),
}

fn part1(file_content: &str) {
    let mut sum: i32 = 0;
    let mut total_sum: i32 = 0;
    let (red_max, green_max, blue_max) = (12, 13, 14);

    'line_loop: for line in file_content.lines() {
        let mut basic_split_line = line.split(": ");
        let game_info = basic_split_line.next().unwrap();
        let game_data = basic_split_line.next().unwrap();

        let game_number: i32 = str::parse::<i32>(game_info.split(' ').last().unwrap()).unwrap();
        let game_cubes = get_game_cubes(game_data);

        total_sum += game_number;

        for cube in game_cubes {
            match cube {
                Cube::Red(count) => {
                    if count > red_max {
                        sum += game_number;
                        continue 'line_loop;
                    }
                }
                Cube::Green(count) => {
                    if count > green_max {
                        sum += game_number;
                        continue 'line_loop;
                    }
                }
                Cube::Blue(count) => {
                    if count > blue_max {
                        sum += game_number;
                        continue 'line_loop;
                    }
                }
            }
        }
    }
    let remainder = total_sum - sum;
    println!("part 1 result : {remainder}");
}

fn get_game_cubes(game_data: &str) -> Vec<Cube> {
    let mut game_cubes: Vec<Cube> = Vec::new();

    for segments in game_data.split("; ") {
        for cube_data in segments.split(", ") {
            let cube_count: i32 = (cube_data.split(' ').next().unwrap())
                .parse::<i32>()
                .unwrap();

            let cube: Cube = match cube_data.split(' ').last().unwrap() {
                "red" => Cube::Red(cube_count),
                "green" => Cube::Green(cube_count),
                "blue" => Cube::Blue(cube_count),
                _ => panic!("error parsing cube data : {game_data}"),
            };

            game_cubes.push(cube);
        }
    }

    return game_cubes;
}

fn part2(file_content: &str) {
    let mut sum: i32 = 0;

    for line in file_content.lines() {
        let mut basic_split_line = line.split(": ");
        let _ = basic_split_line.next().unwrap();
        let game_data = basic_split_line.next().unwrap();

        let game_cubes = get_game_cubes(game_data);

        let (mut red_max, mut green_max, mut blue_max) = (i32::MIN, i32::MIN, i32::MIN);

        for cube in game_cubes {
            match cube {
                Cube::Red(count) => {
                    if count > red_max {
                        red_max = count
                    }
                }
                Cube::Green(count) => {
                    if count > green_max {
                        green_max = count
                    }
                }
                Cube::Blue(count) => {
                    if count > blue_max {
                        blue_max = count
                    }
                }
            }
        }

        sum += red_max * green_max * blue_max;
    }

    println!("part 2 result : {sum}")
}
