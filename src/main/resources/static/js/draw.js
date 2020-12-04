const colorArr = ["#789", "#abcdef", "#cff", "#9cf", "#99f", "#90f", "#3ff", "#009", "#606", "#f69", "#c66", "#963", "#c9c", "#360", "#693", "#036"];
const gridLength = 200;


function drawGrid() {
    let fromBig = this.data.fromBig;
    this.ctx.strokeStyle = "#f3f3f3";
    let xStart = this.xStart;
    let xEnd = this.xEnd;
    let yEnd = this.yEnd;
    let yStart = this.yStart;

    //	绘制竖线
    for (let i = 0; i < this.xCount; i++) {
        let w = ((xEnd - xStart) / this.xCount) * (i + 1) + xStart;

        this.ctx.moveTo(w, yStart);
        this.ctx.lineTo(w, yEnd);
        this.ctx.textAlign = "center";
        this.ctx.fillText(this.data.index[i], w, yStart + 10);
    }

    //	绘制横线
    for (let i = 0; i <= this.yCount; i++) {  //0 -
        let w = yStart - (yStart - yEnd) / this.yCount * i;
        this.ctx.moveTo(xStart, w);
        this.ctx.lineTo(xEnd, w);
        this.ctx.textAlign = "right";
        if (fromBig === 1) {
            this.ctx.fillText(i * this.data.stepHeight, xStart - 10, w);
        } else {
            this.ctx.fillText(this.data.height - i * this.data.stepHeight + 1, xStart - 10, w);
        }
    }

    this.ctx.stroke();
}

function drawLine(data) {
    let fromBig = this.data.fromBig;
    this.ctx.strokeStyle = "#f3f3f3";
    let xStart = this.xStart;
    let xEnd = this.xEnd;
    let yEnd = this.yEnd;
    let yStart = this.yStart;
    let lastLocalMap = new Map();
    // 绘制折线并添加数据
    for (let i = 0; i < data.datasets.length; i++) {
        this.ctx.beginPath();
        this.ctx.strokeStyle = colorArr[i % colorArr.length];
        let lastLocal = 0;
        for (let j = 0; j < this.xCount; j++) {
            let w = ((xEnd - xStart) / this.xCount) * (j + 1) + xStart;
            let score = data.datasets[i].scores[j];

            //缺席跳过
            if (score === -1) {
                continue
            }

            let local
            if (fromBig === 1) {
                local = Math.floor(yStart - (yStart - yEnd) / data.height * score);
            } else {
                local = Math.floor((yStart - yEnd) / data.height * (score - 1) + xStart);
            }
            lastLocal = local

            // 绘制折线
            if (j === 0) {
                this.ctx.moveTo(w, local);
            } else {
                this.ctx.lineTo(w, local);
            }
            this.ctx.textAlign = "left";
            this.ctx.fillStyle = "#ff0000";

            // 添加数据
            this.ctx.fillText(score, w, local);
        }

        let addLength;
        if (!lastLocalMap.has(lastLocal)) {
            addLength = 15;
        } else {
            addLength = lastLocalMap.get(lastLocal);
        }
        this.ctx.fillStyle = "#000";
        this.ctx.fillText(data.datasets[i].name, xEnd + addLength, lastLocal);
        this.ctx.stroke();

        if (data.datasets[i].name != null) {
            let length = data.datasets[i].name.length * 10;
            addLength += length;
            addLength += 10;
        }
        lastLocalMap.set(lastLocal, addLength);
    }
}

function clear(id){
    this.canvas = document.getElementById(id);
    this.ctx = this.canvas.getContext('2d');
    this.ctx.clearRect(0,0,this.canvas.width,this.canvas.height);
}

let line = function (data,id) {

    this.canvas = document.getElementById(id);
    this.ctx = this.canvas.getContext('2d');
    this.ctx.clearRect(0,0,this.canvas.width,this.canvas.height);

    let score = data.datasets[0].scores;
    this.xCount = data.datasets[0].scores.length;	// X轴的点数（竖线的个数）
    this.yCount = data.height / data.stepHeight;

    this.data = data;

    this. xStart = gridLength / 5;//default width: 1000  xStart = 800
    this. xEnd = this.canvas.width - 4 * gridLength;//default width: 1000  xEnd = 200
    this. yEnd = gridLength / 5;//default height: 800 yEnd = 100
    this. yStart = this.canvas.height - this.yEnd;//default height: 800  yStart = 700

    drawGrid();
    drawLine(data);
}