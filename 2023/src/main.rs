use std::env;

pub mod day1;
pub mod day2;
pub mod day3;

pub fn main() {
    let args: Vec<String> = env::args().collect();

    match args.get(1) {
        None => {
            println!("No day specified in arguments at launch")
        }
        Some(day_string) => launch_day(&day_string),
    }
}

fn launch_day(day: &str) {
    match day {
        "day1" => day1::launch(),
        "day2" => day2::launch(),
        "day3" => day3::launch(),
        _ => {
            println!("Day {day} doesn't exist")
        }
    }
}
