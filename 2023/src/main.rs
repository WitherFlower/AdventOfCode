use std::{fs, env};

pub mod day1;

pub fn main() {
    let args: Vec<String> = env::args().collect();

    match args.get(1) {
        None => { println!("No day specified in arguments at launch") },
        Some(day_string) => { launch_day(&day_string) }
    }
}

fn launch_day(day: &str) {
    match day {
        "day1" => { day1::launch() }
        _ => { println!("Day {day} doesn't exist") }
    }
}