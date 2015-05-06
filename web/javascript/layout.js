d3.select("body").append("div")
	.attr("class","gyro-div");

d3.select("body").append("div")
	.attr("class","acc-div");

generateGraph("div.gyro-div","../tsv/gyroscope.tsv");
generateGraph("div.acc-div","../tsv/accelerometer.tsv");