var gyroscope = d3.select("body").append("div")
				.attr("class","gyro-div");

var accelerometer = d3.select("body").append("div")
				.attr("class","acc-div");

gyroscope.append("h2")
	.text("Gyroscope");

accelerometer.append("h2")
	.text("Accelerometer");

generateGraph("div.gyro-div","../tsv/gyroscope.tsv");
generateGraph("div.acc-div","../tsv/accelerometer.tsv");