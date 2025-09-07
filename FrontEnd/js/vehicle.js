const apiUrl = "http://localhost:8080/api/v1/vehicles";
let editVehicleId = null;

// ======================= LOAD VEHICLES =======================
function loadVehicles() {
    $.get(`${apiUrl}/all`, function(res) {
        // Remove res.success check since backend doesn't have it
        if (Array.isArray(res.data)) {
            const container = $("#vehicleCards");
            container.empty();

            res.data.forEach(vehicle => {
                const imgSrc = vehicle.image ? `http://localhost:8080/${vehicle.image}` : 'https://via.placeholder.com/300x200';

                console.log(vehicle.image);
                const card = $(`
                    <div class="col-md-4 mb-4">
                        <div class="card shadow-sm">
                            <img src="${imgSrc}" class="card-img-top" style="height:200px; object-fit:cover;">
                            <div class="card-body">
                                <h5 class="card-title">${vehicle.name}</h5>
                                <p class="card-text">
                                    <strong>Number Plate:</strong> ${vehicle.numberPlate}<br>
                                    <strong>Type:</strong> ${vehicle.type}<br>
                                    <strong>Model:</strong> ${vehicle.model}<br>
                                    <strong>Status:</strong> <span class="badge ${vehicle.status === 'Available' ? 'bg-success' : 'bg-danger'}">${vehicle.status}</span>
                                </p>
                                <div class="d-flex justify-content-end">
                                    <button class="btn btn-sm btn-primary me-2 edit-btn" data-id="${vehicle.id}">
                                        <i class="bi bi-pencil-square"></i>Edit
                                    </button>
                                    <button class="btn btn-sm btn-danger delete-btn" data-id="${vehicle.id}">
                                        <i class="bi bi-trash"></i>Delete
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                `);
                container.append(card);
            });
        } else {
            alert("Failed to load vehicles: " + res.message);
        }
    }).fail(function(xhr, status, error) {
        console.error("AJAX error:", error);
        alert("Failed to load vehicles due to server error");
    });
}

// ======================= ADD / UPDATE VEHICLE =======================
$("#vehicleForm").submit(function(e) {
    e.preventDefault();

    const formData = new FormData();
    formData.append("name", $("#name").val());
    formData.append("numberPlate", $("#numberPlate").val());
    formData.append("type", $("#type").val());
    formData.append("model", $("#model").val());
    formData.append("status", $("#status").val());

    const imageFile = $("#image")[0].files[0];
    if (imageFile) formData.append("image", imageFile);

    let url = apiUrl + "/add";
    let method = "POST";

    if (editVehicleId) {
        url = `${apiUrl}/update/${editVehicleId}`;
        method = "PUT";
    }

    $.ajax({
        url: url,
        type: method,
        data: formData,
        contentType: false,
        processData: false,
        success: function(res) {
            alert(res.message);
            $("#vehicleForm")[0].reset();
            $("#previewImage").hide();
            $("#formTitle").text("Add Vehicle");
            $("#submitBtn").text("Add Vehicle");
            $("#cancelBtn").hide();
            editVehicleId = null;
            loadVehicles();
        },
        error: function(xhr, status, error) {
            console.error("Error:", error);
            alert("Failed to save vehicle.");
        }
    });
});

// ======================= IMAGE PREVIEW =======================
$("#image").on("change", function() {
    const file = this.files[0];
    if(file){
        $("#previewImage").attr("src", URL.createObjectURL(file)).show();
    } else {
        $("#previewImage").hide();
    }
});

// ======================= EDIT VEHICLE =======================
$(document).on("click", ".edit-btn", function() {
    editVehicleId = $(this).data("id");

    $.get(`${apiUrl}/${editVehicleId}`, function(res) {
        if(res.data) { // Use res.data instead of res.success
            const v = res.data;
            $("#name").val(v.name);
            $("#numberPlate").val(v.numberPlate);
            $("#type").val(v.type);
            $("#model").val(v.model);
            $("#status").val(v.status);

            if (v.image) {
                $("#previewImage").attr("src", `http://localhost:8080/${v.image}`).show();
            } else {
                $("#previewImage").hide();
            }

            $("#formTitle").text("Edit Vehicle");
            $("#submitBtn").text("Update Vehicle");
            $("#cancelBtn").show();
        } else {
            alert("Failed to load vehicle data");
        }
    });
});

// ======================= CANCEL EDIT =======================
$("#cancelBtn").click(function() {
    $("#vehicleForm")[0].reset();
    $("#previewImage").hide();
    $("#formTitle").text("Add Vehicle");
    $("#submitBtn").text("Add Vehicle");
    $(this).hide();
    editVehicleId = null;
});

// ======================= DELETE VEHICLE =======================
$(document).on("click", ".delete-btn", function() {
    const id = $(this).data("id");
    if(confirm("Are you sure you want to delete this vehicle?")) {
        $.ajax({
            url: `${apiUrl}/delete/${id}`,
            type: "DELETE",
            success: function(res) {
                alert(res.message);
                loadVehicles();
            },
            error: function(xhr, status, error) {
                console.error("Error:", error);
                alert("Failed to delete vehicle.");
            }
        });
    }
});

// ======================= INITIAL LOAD =======================
$(document).ready(function() {
    loadVehicles();
    $("#cancelBtn").hide();
});
