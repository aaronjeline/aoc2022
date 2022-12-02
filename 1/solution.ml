
let (|>) x f = f x

let filename = "input"

type elf = Elf of int

let string_of_elf (Elf i) = Printf.printf "Elf %d" i

type line = 
        | Cal of int
        | Break

type state = 
        { current_cals : int
        ; elfs : elf list
        }

let new_state () = ref ({ current_cals = 0; elfs = [] })

let modify f s = s := f (!s)

let add_cal c ({ current_cals; elfs }) = 
        { current_cals = current_cals + c; elfs }

let break_elf ({ current_cals; elfs }) = 
        { current_cals = 0; elfs = (Elf current_cals) :: elfs }

let solution s =  
        let { current_cals; elfs }  = !s in 
        let sorted = List.sort (fun x y -> ~- (compare x y)) elfs in
        match (List.nth sorted 0, List.nth sorted 1, List.nth sorted 2) with 
        | (Elf a, Elf b, Elf c) ->  a + b + c

let update st line = 
        let f = match line with
                | Cal i -> add_cal i
                | Break -> break_elf
        in modify f st

let parse_line l = 
        match int_of_string_opt l with
        | Some i -> Cal i
        | None -> Break


let () = 
        let s = new_state () in
        let chan = open_in filename in
        try
                while true; do
                        let input = chan |> input_line |> parse_line in 
                        update s input
                done;
        with End_of_file -> 
                close_in chan;
                Printf.printf "%d\n" (solution s)

