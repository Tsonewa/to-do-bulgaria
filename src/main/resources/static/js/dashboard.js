const ulParent = document.getElementById("nav-ul");

let systemStatus = "";

const displaySystemStatus = (systemStatus) => {

    let li = document.createElement("li");
    li.className = "nav-li";
    let i = document.createElement("i");
    i.className = "fab fa-windows";
    let span = document.createElement("span");
    span.textContent = `System: ${systemStatus}`;

    li.append(i);
    li.append(span);

    ulParent.append(li);
    }

fetch("http://localhost:8080/actuator/health/ping")
        .then(response => response.json())
        .then(data => {
            systemStatus = data.status;
            displaySystemStatus(systemStatus);
        });

let dbStatus = "";
let dbName = "";

const displayDatabaseStatus = (dbName, dbStatus) => {

    let li = document.createElement("li");
    li.className = "nav-li";
    let i = document.createElement("i");
    i.className = "fas fa-server";
    let span = document.createElement("span");
    span.textContent = `DB: ${dbName} - ${dbStatus}`;

    li.append(i);
    li.append(span);

    ulParent.append(li);
}

fetch("http://localhost:8080/actuator/health/db")
    .then(response => response.json())
    .then(data => {
        let details = data.details;
        dbName = Object.values(details)[0];
        dbStatus = data.status;
        displayDatabaseStatus(dbName,dbStatus);
    });

let freeDiscSpace = "";

const displayFreeDiscSpace = (freeDiscSpace) => {

    let li = document.createElement("li");
    li.className = "nav-li";
    let i = document.createElement("i");
    i.className = "fas fa-memory";
    let span = document.createElement("span");
    span.textContent = `Disk Space: ${freeDiscSpace}`;

    li.append(i);
    li.append(span);

    ulParent.append(li);
}

fetch("http://localhost:8080/actuator/health/diskSpace")
    .then(response => response.json())
    .then(data => {
        let details = data.details;
        freeDiscSpace = formatBytes(Object.values(details)[1]);
        displayFreeDiscSpace(freeDiscSpace);
    });

function formatBytes(x){
    const units = ['bytes', 'KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB'];
    let l = 0, n = parseInt(x, 10) || 0;
    while(n >= 1024 && ++l){
        n = n/1024;
    }
    return(n.toFixed(n < 10 && l > 0 ? 1 : 0) + ' ' + units[l]);
}

let upTime = "";

const displayUpTime = (upTime) => {

    let li = document.createElement("li");
    li.className = "nav-li";
    let i = document.createElement("i");
    i.className = "fas fa-clock";
    let span = document.createElement("span");
    span.textContent = `Up time: ${upTime}`;

    li.append(i);
    li.append(span);

    ulParent.append(li);
}

fetch("http://localhost:8080/actuator/metrics/process.uptime")
    .then(response => response.json())
    .then(data => {
         let upKey = Object.keys(data)[3];
         let measurmentsValue = data[upKey];
         upTime = secondsToDhms(Object.values(measurmentsValue)[0].value);
        displayUpTime(upTime);
    });

function secondsToDhms(seconds) {
    seconds = Number(seconds);
    var d = Math.floor(seconds / (3600*24));
    var h = Math.floor(seconds % (3600*24) / 3600);
    var m = Math.floor(seconds % 3600 / 60);
    var s = Math.floor(seconds % 60);

    var dDisplay = d > 0 ? d + (d === 1 ? " day, " : " d, ") : "";
    var hDisplay = h > 0 ? h + (h === 1 ? " hour, " : " h, ") : "";
    var mDisplay = m > 0 ? m + (m === 1 ? " minute, " : " min, ") : "";
    var sDisplay = s > 0 ? s + (s === 1 ? " second" : " sec") : "";
    return dDisplay + hDisplay + mDisplay + sDisplay;
}

let processorsCount = 0;

const displayProcessors = (processorsCount) => {

    let li = document.createElement("li");
    li.className = "nav-li";
    let i = document.createElement("i");
    i.className = "fas fa-microchip";
    let span = document.createElement("span");
    span.textContent = `Processors: ${processorsCount}`;

    li.append(i);
    li.append(span);

    ulParent.append(li);
}

fetch("http://localhost:8080/actuator/metrics/system.cpu.count")
    .then(response => response.json())
    .then(data => {
        let upKey = Object.keys(data)[3];
        let measurmentsValue = data[upKey];
        let array = Object.values(measurmentsValue[0]);
        processorsCount = array[1];
        displayProcessors(processorsCount.toFixed());
    });


const containerCards = document.getElementById('container-cards');
const containerTableRows = document.getElementById('traces-table');

let httpTracesStatus200 = [];
let httpTracesStatus400 = [];
let httpTracesStatus404 = [];
let httpTracesStatus500 = [];
let httpDefaultTraces = [];

fetch("http://localhost:8080/actuator/httptrace")
    .then(response => response.json())
    .then(data => {
        let traceKey = Object.keys(data)[0];
        let arrayTraces = data[traceKey];
        for (const trace of arrayTraces) {
            processTraces(trace);
            visualizeTraceRow(trace);
        }
        displayTraces200(httpTracesStatus200);
        displayTraces400(httpTracesStatus400);
        displayTraces404(httpTracesStatus404);
        displayTraces500(httpTracesStatus500);
    });

function visualizeTraceRow(trace){

    let tr = document.createElement('tr');
    let tdTimestamp = document.createElement('td');
    tdTimestamp.textContent = `${trace.timestamp}`;
    tr.appendChild(tdTimestamp);
    let tdMethod = document.createElement('td');
    tdMethod.textContent = `${trace.request.method}`;
    tr.appendChild(tdMethod);
    let tdTimetaken = document.createElement('td');
    tdTimetaken.textContent = `${trace.timeTaken}`;
    tr.appendChild(tdTimetaken);
    let tdResponseStat = document.createElement('td');
    tdResponseStat.textContent = `${trace.response.status}`;
    tr.appendChild(tdResponseStat);
    let tdResponseUrl = document.createElement('td');
    tdResponseUrl.textContent = `${trace.request.uri}`;
    tr.appendChild(tdResponseUrl);

    containerTableRows.appendChild(tr);
}

function displayTraces200(traceArray){
    let divRow = document.createElement('div');
    divRow.className = "row";
    let divGreen = document.createElement('div');
    divGreen.className = "green";
    let divCard = document.createElement('div');
    divCard.className = "card";

    let divBlock = document.createElement("div");
    divBlock.className = "card-block";

    let h3 = document.createElement("h3");
    let i = document.createElement("i");
    i.className = "far fa-check-circle";
    let span = document.createElement('span');
    span.textContent = `${traceArray.length}`;
    h3.appendChild(i);
    h3.textContent = '200 Response';
    h3.append(span);


    let p = document.createElement("p");
    let date = new Date(traceArray[0].timestamp).toLocaleDateString();
    let spanDate = document.createElement('span');
    spanDate.textContent = `${date}`;
    p.textContent = `Updated `;
    p.append(spanDate);

    divBlock.appendChild(h3);
    divBlock.appendChild(p);

    divCard.appendChild(divBlock);
    divGreen.appendChild(divCard);
    divRow.appendChild(divGreen);

    containerCards.appendChild(divRow);
}

function displayTraces400(traceArray){
    let divRow = document.createElement('div');
    divRow.className = "row";
    let divOrange = document.createElement('div');
    divOrange.className = "orange";
    let divCard = document.createElement('div');
    divCard.className = "card";

    let divBlock = document.createElement("div");
    divBlock.className = "card-block";

    let h3 = document.createElement("h3");
    let i = document.createElement("i");
    i.className = "far fa-exclamation-circle";
    let span = document.createElement('span');
    span.textContent = traceArray.length === 0 ? '0' : `${traceArray.length}`;
    h3.appendChild(i);
    h3.textContent = '400 Response';
    h3.append(span);


    let p = document.createElement("p");
    let date = traceArray.length === 0 ? new Date().toLocaleDateString() : new Date(traceArray[0].timestamp).toLocaleDateString();
    let spanDate = document.createElement('span');
    spanDate.textContent = `${date}`;
    p.textContent = `Updated `;
    p.append(spanDate);

    divBlock.appendChild(h3);
    divBlock.appendChild(p);

    divCard.appendChild(divBlock);
    divOrange.appendChild(divCard);
    divRow.appendChild(divOrange);

    containerCards.appendChild(divRow);
}

    function displayTraces404(traceArray){
    let divRow = document.createElement('div');
    divRow.className = "row";
    let divRed = document.createElement('div');
    divRed.className = "red";
    let divCard = document.createElement('div');
    divCard.className = "card";

    let divBlock = document.createElement("div");
    divBlock.className = "card-block";

    let h3 = document.createElement("h3");
    let i = document.createElement("i");
    i.className = "far fa-ban";
    let span = document.createElement('span');
    span.textContent = traceArray.length === 0 ? '0' : `${traceArray.length}`;
    h3.appendChild(i);
    h3.textContent = '404 Response';
    h3.append(span);


    let p = document.createElement("p");
    let date = traceArray.length === 0 ? new Date().toLocaleDateString() : new Date(traceArray[0].timestamp).toLocaleDateString();
    let spanDate = document.createElement('span');
    spanDate.textContent = `${date}`;
    p.textContent = `Updated `;
    p.append(spanDate);

    divBlock.appendChild(h3);
    divBlock.appendChild(p);

    divCard.appendChild(divBlock);
    divRed.appendChild(divCard);
    divRow.appendChild(divRed);

    containerCards.appendChild(divRow);
}

function displayTraces500(traceArray){
    let divRow = document.createElement('div');
    divRow.className = "row";
    let blueDiv = document.createElement('div');
    blueDiv.className = "blue";
    let divCard = document.createElement('div');
    divCard.className = "card";

    let divBlock = document.createElement("div");
    divBlock.className = "card-block";

    let h3 = document.createElement("h3");
    let i = document.createElement("i");
    i.className = "far fa-bug";
    let span = document.createElement('span');
    span.textContent = traceArray.length === 0 ? '0' : `${traceArray.length}`;
    h3.appendChild(i);
    h3.textContent = '500 Response';
    h3.append(span);


    let p = document.createElement("p");
    let date = traceArray.length === 0 ? new Date().toLocaleDateString() : new Date(traceArray[0].timestamp).toLocaleDateString();
    let spanDate = document.createElement('span');
    spanDate.textContent = `${date}`;
    p.textContent = `Updated `;
    p.append(spanDate);

    divBlock.appendChild(h3);
    divBlock.appendChild(p);

    divCard.appendChild(divBlock);
    blueDiv.appendChild(divCard);
    divRow.appendChild(blueDiv);

    containerCards.appendChild(divRow);
}

    function processTraces(trace) {

        switch (trace.response.status) {
            case 200:
                httpTracesStatus200.push(trace);
                break;
            case 404:
                httpTracesStatus404.push(trace);
                break;
            case 400:
                httpTracesStatus400.push(trace);
                break;
            case 500:
                httpTracesStatus500.push(trace);
                break;
            default:
                httpDefaultTraces.push(trace);
                break;
        }
    };

