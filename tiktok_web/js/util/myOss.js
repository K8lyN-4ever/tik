import '../public/aliyun-oss-sdk-6.17.0.min.js';

const myRegion = 'oss-cn-hangzhou';
const myBucket = 'tik-videos-server';

function generateUUID() {
    let d = new Date().getTime();
    return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
        const r = (d + Math.random() * 16) % 16 | 0;
        d = Math.floor(d / 16);
        return (c === 'x' ? r : (r & 0x3 | 0x8)).toString(16);
    });
}

function formatName(name) {
    const date = new Date();
    const month = date.getMonth() + 1;
    const day = date.getDate();
    const prefix = `/${month < 10 ? '0' + month : month}${day < 10 ? '0' + day : day}` + '/';
    const suffix = '.mp4';
    return prefix.concat(name).concat(suffix);
}

function isTokenExpired(token) {
    const expiration = new Date(token.expiration);
    const now = new Date();
    return expiration.getTime() < now.getTime();
}

function createClient(token) {
    return new OSS({
        region: myRegion,
        accessKeyId: token.credentials.accessKeyId,
        accessKeySecret: token.credentials.accessKeySecret,
        stsToken: token.credentials.securityToken,
        bucket: myBucket
    });
}

export async function multipartUpload(token, file, size, progress) {
    if(!isTokenExpired(token)) {
        let res;
        const client = createClient(token);
        await client.multipartUpload(formatName(generateUUID()), file, {
            progress: (p, cpt, res) => {
                // abortCheckpoint = cpt;
                // console.log(abortCheckpoint);
                progress.value = p * 100;
            }
        }).then((r) => {
            res =  r;
        });
        return res;
    }
}

export function getDownloadUrl(token, filename) {
    if(!isTokenExpired(token)) {
        const client = createClient(token)
        const response = {
            'content-disposition': `attachment; filename=${encodeURIComponent(filename)}`
        };
        return client.signatureUrl(filename, response);
    }
}
