window.onload = function() {
    reloadLists();
};

function reloadLists() {
    getAllStudents();
}

function getAllStudents() {
    $("#students").empty();
    $.get("api/v1/students", function(data) {
        var i;
        var select = document.getElementById("students");
        for (i = 0; i < data.length; i++) {
            var opt = document.createElement("option");
            var obj = data[i];
            opt.value = obj.id;
            opt.innerHTML = obj.name;
            select.appendChild(opt);
        }
        getStudentSubjects();
    });
}

function getStudentSubjects() {
    $("#subjects").empty();
    var index = document.getElementById("students").options.selectedIndex;
    var id = document.getElementById("students").options[index].value;

    $.get("api/v1/students/" + id + "/subjects", function(data) {
        var select = document.getElementById("subjects");
        for (var i = 0; i < data.length; i++) {
            var opt = document.createElement("option");
            var obj = data[i];
            opt.value = obj.id;
            opt.innerHTML = obj.name;
            opt.selected = obj.selected;
            select.appendChild(opt);
        }
    });
}

function saveSubjects() {
    var index = document.getElementById("students").options.selectedIndex;
    var id = document.getElementById("students").options[index].value;
    var subjects = [];
    var j = 0;
    var count = document.getElementById("subjects").options.length;
    for (var i = 0; i < count; i++) {
        if (document.getElementById("subjects").options[i].selected===true)
        {
            subjects[j]=document.getElementById("subjects").options[i].value;
            j++;
        }
    }
    var url = "api/v1/students/" + id + "/subjects";
    var data = JSON.stringify(subjects);
    $.ajax({
        type: 'POST',
        url: url,
        data: data,
        contentType: "application/json",
        dataType:"json"
        })
    .done(function (data) {
    });
}