import axios from 'axios'

let redirectingToLogin = false

function isUnauthorizedMessage(message) {
    return /未登录|登录失效|token|unauthorized|forbidden/i.test(String(message || ''))
}

function clearAuthAndRedirect() {
    localStorage.removeItem('token')
    localStorage.removeItem('userInfo')
    localStorage.removeItem('user')

    if (redirectingToLogin) {
        return
    }

    redirectingToLogin = true
    // 带上当前地址，登录后可回跳到原页面
    const current = `${window.location.pathname}${window.location.search}${window.location.hash}`
    const redirect = encodeURIComponent(current || '/backend/knowledge')
    window.location.href = `/auth/login?redirect=${redirect}`
}

const service = axios.create({
    baseURL: '/api',
    timeout: 5000
})

service.interceptors.request.use(config => {
    const token = localStorage.getItem('token')
    if (token) {
        config.headers['token'] = token
    }
    return config
}, error => {
    return Promise.reject(error)
})

service.interceptors.response.use(response => {
    const { data } = response
    const hasCode = data && data.code !== undefined && data.code !== null
    const code = hasCode ? Number(data.code) : 200
    const message = String(data?.message || data?.msg || '')

    if (code !== 200) {
        if (code === 401 || code === 403 || isUnauthorizedMessage(message)) {
            clearAuthAndRedirect()
        }
        return Promise.reject(new Error(data?.message || 'Error'))
    }

    return response
}, error => {
    const status = error?.response?.status
    const code = Number(error?.response?.data?.code)
    const message = String(
        error?.response?.data?.message ||
        error?.response?.data?.msg ||
        error?.message ||
        ''
    )

    const isUnauthorized =
        status === 401 ||
        status === 403 ||
        code === 401 ||
        code === 403 ||
        isUnauthorizedMessage(message)

    if (isUnauthorized) {
        clearAuthAndRedirect()
    }

    return Promise.reject(error)
})

export default service