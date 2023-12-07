use std::fs;

pub fn launch() {
    let file_path = "input/day1.txt";
    let file_content =
        fs::read_to_string(file_path).expect("Should have been able to read the file");

    part1(&file_content);
    part2(&file_content);
}

fn part1(file_content: &String) {
    let mut sum = 0;

    for line in file_content.lines() {
        let mut digits: Vec<i32> = Vec::new();
        for c in line.chars() {
            if c.is_digit(10) {
                digits.push(c.to_digit(10).unwrap() as i32);
            }
        }
        sum += digits.first().unwrap() * 10 + digits.last().unwrap();
    }

    println!("{sum}");
}

fn part2(file_content: &String) {
    let mut sum = 0;

    let digit_strings = [
        "one", "two", "three", "four", "five", "six", "seven", "eight", "nine",
    ];

    for line in file_content.lines() {
        
        let mut first_digit: i32 = 0;
        let mut first_digit_index: i32 = i32::MAX;
        let mut last_digit: i32 = 0;
        let mut last_digit_index: i32 = i32::MIN;

        for i in 0..9 {
            let indices = line.match_indices(digit_strings[i]);

            for (index, _) in indices {
                if first_digit_index > index as i32 {
                    first_digit_index = index as i32;
                    first_digit = (i as i32) + 1;
                }

                if last_digit_index < index as i32 {
                    last_digit_index = index as i32;
                    last_digit = (i as i32) + 1;
                }
            }
        }

        for i in 1..=9 {
            let i_string = i.to_string();
            let indices = line.match_indices(String::as_str(&i_string));

            for (index, _) in indices {
                if first_digit_index > index as i32 {
                    first_digit_index = index as i32;
                    first_digit = i as i32;
                }

                if last_digit_index < index as i32 {
                    last_digit_index = index as i32;
                    last_digit = i as i32;
                }
            }
        }

        println!("line : {line} | {first_digit}{last_digit}");
        sum += first_digit * 10 + last_digit;
    }
    println!("{sum}");
}
