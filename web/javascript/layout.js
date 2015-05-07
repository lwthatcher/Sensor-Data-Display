function initLayout()
{
	var path = "../tsv/trial_I04.tsv";

	var top = d3.select("body").append("div");
	drawIndex();

	var bottom = d3.select("body").append("div")
					.attr("class","graphs");

	var gyroscope = d3.select("div.graphs").append("div")
					.attr("class","gyro-div");

	var accelerometer = d3.select("div.graphs").append("div")
					.attr("class","acc-div");

	gyroscope.append("h2")
		.text("Gyroscope");
	gyroscope.append("svg");

	accelerometer.append("h2")
		.text("Accelerometer");
	accelerometer.append("svg");

	updateGraphs(path);
}

function updateGraphs(path)
{
	generateGraph("div.gyro-div > svg",path,"gyroscope_x","gyroscope_y","gyroscope_z");
	generateGraph("div.acc-div > svg",path,"accelerometer_x","accelerometer_y","accelerometer_z");
}

function getPath(id)
{
	return "../tsv/trial_" + id + ".tsv";
}

function drawIndex()
{
	var width = 800;
	var height = 200;

	var color = d3.scale.category20();

	var force = d3.layout.force()
			.charge(-120)
            .linkDistance(30)
            .size([width, height]);

	var svg = d3.select("div")
		.append("svg")
		.attr("width", width)
        .attr("height", height);

	d3.json("../tsv/index.json", function(error, graph) {
		console.log(graph);

		force
			.nodes(graph.nodes)
			.links(graph.links)
			.start();

		var link = svg.selectAll(".link")
              .data(graph.links)
            .enter().append("line")
              .attr("class", "link")
              .style("stroke-width", function(d) { return Math.sqrt(d.value); });

          var node = svg.selectAll(".node")
              .data(graph.nodes)
            .enter().append("circle")
              .attr("class", "node")
              .attr("r", 8)
              .on("click", function(d) {
              	path = getPath(d.id);
              	updateGraphs(path);
              })
              .style("fill", function(d) { return color(d.group); })
              .call(force.drag);

          node.append("title")
              .text(function(d) { return d.name; });

          force.on("tick", function() {
            link.attr("x1", function(d) { return d.source.x; })
                .attr("y1", function(d) { return d.source.y; })
                .attr("x2", function(d) { return d.target.x; })
                .attr("y2", function(d) { return d.target.y; });

            node.attr("cx", function(d) { return d.x; })
                .attr("cy", function(d) { return d.y; });
          });
	});
}