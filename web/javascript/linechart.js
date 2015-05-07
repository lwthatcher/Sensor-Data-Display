function generateGraph(selector, datas, data_x, data_y, data_z)
{
	var margin = {top: 20, right: 20, bottom: 30, left: 50},
		width = 700 - margin.left - margin.right,
		height = 400 - margin.top - margin.bottom;

	var x = d3.scale.linear()
		.range([0, width]);

	var y = d3.scale.linear()
		.range([height, 0]);

	var xAxis = d3.svg.axis()
		.scale(x)
		.orient("bottom");

	var yAxis = d3.svg.axis()
		.scale(y)
		.orient("left");

	console.log(selector);

	var svg = d3.select(selector);

	svg.selectAll("*").remove();

	svg
		.attr("width", width + margin.left + margin.right)
		.attr("height", height + margin.top + margin.bottom)
		.append("g")
		.attr("transform", "translate(" + margin.left + "," + margin.top + ")");

	d3.tsv(datas, function(error, data) {
	  data.forEach(function(d) {
		d.time = +d.time;
		d.x = +d[data_x];
		d.y = +d[data_y];
		d.z = +d[data_z];
	  });

		var lineGen = function(dim) {
			var xScale = d3.scale.linear()
				.range([0, width])
				.domain(d3.extent(data, function(d) { return d.time; }));
			var yScale = d3.scale.linear()
				.range([height, 0])
				.domain([-30000,25000]);
			var line = d3.svg.line()
			.x(function(d) { return xScale(d.time); })
			.y(function(d) { return yScale(d[dim]); });
			return line;
	  }

	  x.domain(d3.extent(data, function(d) { return d.time; }));
	  y.domain(d3.extent(data, function(d) { return d.x; }));

	  svg.append("g")
		  .attr("class", "x axis")
		  .attr("transform", "translate(0," + height + ")")
		  .call(xAxis);

	  svg.append("g")
		  .attr("class", "y axis")
		  .call(yAxis)
		.append("text")
		  .attr("transform", "rotate(-90)")
		  .attr("y", 6)
		  .attr("dy", ".71em")
		  .style("text-anchor", "end")

	  svg.append("path")
		  .datum(data)
		  .attr("class", "line gyro-x")
		  .attr("d", lineGen('x'));

	svg.append("path")
		  .datum(data)
		  .attr("class", "line gyro-y")
		  .attr("d", lineGen('y'));

	svg.append("path")
		  .datum(data)
		  .attr("class", "line gyro-z")
		  .attr("d", lineGen('z'));
	});
}