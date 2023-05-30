import {multipartUpload} from "../../../../webProject/Tiktok-web/js/util/myOss.js";

$(document).ready(function () {

    const dropzone = document.getElementById('file-dropzone');
    const progressBar = document.getElementById('progress-bar');
    const uploadInput = document.getElementById('upload-input');
    const postBtn = document.getElementById('post-btn');

// 阻止默认拖放行为
    dropzone.addEventListener('dragover', (event) => {
        event.preventDefault();
    });

// 处理文件拖放
    dropzone.addEventListener('drop', (event) => {
        event.preventDefault();
        const file = event.dataTransfer.files[0];
        uploadFile(file);
    });

    dropzone.addEventListener('click', (event) => {
        setTimeout(() => {
            uploadInput.click();
        }, 0);
    });

    uploadInput.addEventListener('change', (event) => {
       const file = event.target.files[0];
       uploadFile(file);
    });

    postBtn.addEventListener('click', (event) => {
       const pathVal = $("#path-input").val();
       const titleVal = $("#title-input").val();
       const descVal = $("#desc-input").val();
       if(pathVal !== "" && titleVal !== "" && descVal !== "") {
           const phoneVal = $.cookie('phone');
            $.ajax({
                url: "http://localhost:8090/api/v1/video/phone/".concat(phoneVal).concat("/post"),
                type: "post",
                data: {
                    title: titleVal,
                    desc: descVal,
                    path: pathVal
                },
                success: function(res) {
                    const str = $.parseJSON(res);
                    alert(str.message);
                    if(str.status === 200) {
                        window.location.href = "../../../../webProject/Tiktok-web/index.html";
                    }
                }
            })
       }else {
           alert("请先上传视频或填写信息");
       }
    });

// 上传文件
    function uploadFile(file) {
        $.ajax({
            url: "http://localhost:8090/api/v1/video/getToken",
            type: "get",
            success: function (res) {
                const str = $.parseJSON(res);
                if(str.status === 200) {
                    multipartUpload(str.data, file, file.size, progressBar).then(r => {
                        $("#path-input").val(r.name);
                    });
                }
            },
        })
    }

})

