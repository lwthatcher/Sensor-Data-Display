var path = "../tsv/trial_03.tsv"

var gyroscope = d3.select("body").append("div")
				.attr("class","gyro-div");

var accelerometer = d3.select("body").append("div")
				.attr("class","acc-div");

gyroscope.append("h2")
	.text("Gyroscope");

accelerometer.append("h2")
	.text("Accelerometer");

generateGraph("div.gyro-div",path,"gyroscope_x","gyroscope_y","gyroscope_z");
generateGraph("div.acc-div",path,"accelerometer_x","accelerometer_y","accelerometer_z");