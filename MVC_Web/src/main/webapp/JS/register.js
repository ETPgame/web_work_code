$(function () {
    $('#uname').blur(function () {
        let name = $(':input[name="username"]').val();
        if (!/^[a-zA-Z0-9]+$/.test(name)) {
            $("#unameTips").html("用户名只能由数字和字母组成").css("color", "red");
            return;
        } else {
            $("#unameTips").html("").css("color", "red");
        }
        $.ajax({
            url: "/userExistServlet",
            type: "get",
            data: "username=" + name,
            success: function (data) {
                if (data == "true") {
                    $("#unameTips").html("用户名已存在").css("color", "red");
                } else {
                    $("#unameTips").html("").css("color", "red");
                }
            }
        });
    });

    $('#upwd').blur(function () {
        var password = document.getElementById('upwd').value;
        if (password.length < 6 || !/^[a-zA-Z0-9]+$/.test(password)) {
            $("#upwdTips").html("密码至少6位，且只能由数字和字母组成").css("color", "red");
        } else {
            $("#upwdTips").html("").css("color", "red");
        }
    });

    $('#uemail').blur(function () {
        var email = document.getElementById('uemail').value;
        var emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!emailRegex.test(email)) {
            $("#emailTips").html("邮箱格式不正确").css("color", "red");
        } else {
            $("#emailTips").html("").css("color", "red");
        }
    });

    populateDropdown("#province", province);

    // 省份选择变化时触发
    $("#province").change(function () {
        var selectedProvince = $(this).val();
        var cities = findCity(selectedProvince);
        populateDropdown("#city", cities);

        // 立即加载第一个市的区域信息
        var firstCity = cities.length > 0 ? cities[0].name : null;
        var districts = findDistrict(firstCity);
        populateDropdown("#district", districts);
    });

    // 城市选择变化时触发
    $("#city").change(function () {
        var selectedCity = $(this).val();
        var districts = findDistrict(selectedCity);
        populateDropdown("#district", districts);
    });

    function populateDropdown(elementId, data) {
        var dropdown = $(elementId);
        dropdown.empty();
        $.each(data, function (key, entry) {
            var value = entry.name || entry; // 取出对象的属性值
            dropdown.append($('<option></option>').attr('value', value).text(value));
        });
    }

    function findCity(provinceName) {
        for (var i = 0; i < province.length; i++) {
            if (province[i].name === provinceName) {
                return province[i].city;
            }
        }
        return [];
    }

    function findDistrict(cityName) {
        for (var i = 0; i < province.length; i++) {
            for (var j = 0; j < province[i].city.length; j++) {
                if (province[i].city[j].name === cityName) {
                    return province[i].city[j].districtAndCounty;
                }
            }
        }
        return [];
    }

    $("#province").trigger("change");
});
