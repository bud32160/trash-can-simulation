Trash Simulation

Simulates the time and distance saving possibilities depending on the automatically generated
random fill-levels of the trash cans given in the input sheets.

#input folder

input_data
Input Excel sheet with general input data
-count of sensors
-percentage of empty cans
-driving and emptying times
-number of iterations

filename: "InputData"

can_list
Input Excel sheet with trash can data
- list of all trash cans for simulation

filename: "TourSheet"

generates:

#output folder (depends on number of iterations)
- Output Excel sheets for every single tour result
- Output Excel sheet for complete Simulation result
- Output Excel sheet with only full oder overfull cans for every Tour result
- Output .tsp file for ACO algorithm test run
