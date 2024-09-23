module.exports = {
    resolve: {
        fallback: {
            "module": false,
            os: require.resolve('os-browserify/browser'),
            path: require.resolve('path-browserify'),
            fs: false, // 브라우저에서는 fs를 사용할 수 없으므로 false로 설정
            crypto: require.resolve('crypto-browserify'),
            stream: require.resolve('stream-browserify'),
            assert: require.resolve('assert/'),
            util: require.resolve('util/'),
        },
    },
};