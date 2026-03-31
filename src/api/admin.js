import axios from 'axios'
import service from '@/utils/request'

export function login(data) {
    return service.post('/user/login', data).catch(error => {
        const status = error?.response?.status
        if (status === 404 || status === 405) {
            return service.post('/auth/login', data)
        }
        throw error
    })
}

export function getCategory(token) {
    return service.get('/knowledge/category/tree', {
        headers: {
            token
        }
    })
}

export function getArticle(params) {
    return service.get('/knowledge/article/page', {
        params
    })
}

export function getArticleDetail(id) {
    const articleId = String(id || '').trim()
    if (!articleId) {
        return Promise.reject(new Error('文章ID不能为空'))
    }

    return service.get(`/knowledge/article/${articleId}`).catch(error => {
        const status = error?.response?.status
        if (status === 404 || status === 405) {
            return service.get('/knowledge/article/detail', {
                params: { id: articleId }
            })
        }
        throw error
    })
}

export function uploadFile(file, businessInfo) {
    const formData = new FormData()
    formData.append('file', file)
    formData.append('businessType', businessInfo?.businessType || 'ARTICLE')
    formData.append('businessId', String(businessInfo?.businessId ?? '0'))
    formData.append('businessField', businessInfo?.businessField || 'cover')

    const token = localStorage.getItem('token') || ''
    return axios.post('/api/file/upload', formData, {
        headers: {
            token
        }
    })
}

export function addArticle(data) {
    return service.post('/knowledge/article', data)
}

export function updateArticle(data) {
    // 文档约定：新增/编辑统一使用 POST /knowledge/article，通过 id 区分
    return service.post('/knowledge/article', data)
}

export function updateArticleStatus(id, status) {
    const articleId = String(id || '').trim()
    if (!articleId) {
        return Promise.reject(new Error('文章ID不能为空'))
    }

    return service.put(`/knowledge/article/${articleId}/status`, { status }).catch(error => {
        const code = error?.response?.status
        if (code === 404 || code === 405) {
            return service.put('/knowledge/article/status', {
                id: articleId,
                status
            })
        }
        throw error
    })
}

export function deleteArticle(id) {
    const articleId = String(id || '').trim()
    if (!articleId) {
        return Promise.reject(new Error('文章ID不能为空'))
    }

    return service.delete(`/knowledge/article/${articleId}`).catch(error => {
        const code = error?.response?.status
        if (code === 404 || code === 405) {
            return service.delete('/knowledge/article', {
                params: { id: articleId }
            })
        }
        throw error
    })
}

export function getQueryList(params) {
    return service.get('/psychological-chat/sessions', { params })
}

export function getQueryDetail(id) {
    const sessionId = String(id || '').trim()
    if (!sessionId) {
        return Promise.reject(new Error('会话ID不能为空'))
    }
    return service.get(`/psychological-chat/sessions/${sessionId}`)
}

export function updateQuerySession(id, data) {
    const sessionId = String(id || '').trim()
    if (!sessionId) {
        return Promise.reject(new Error('会话ID不能为空'))
    }
    return service.put(`/psychological-chat/sessions/${sessionId}`, data).catch(error => {
        const status = error?.response?.status
        if (status === 404 || status === 405) {
            return service.post(`/psychological-chat/sessions/${sessionId}`, data)
        }
        throw error
    })
}

export function getEmotionDiary(params) {
    return service.get('/emotion-diary/admin/page', { params })
}

export function deleteEmotionDiary(id) {
    const diaryId = String(id || '').trim()
    if (!diaryId) {
        return Promise.reject(new Error('日记ID不能为空'))
    }
    return service.delete(`/emotion-diary/admin/${diaryId}`)
}

export function getSessionMessages(sessionId) {
    const id = String(sessionId || '').trim()
    if (!id) {
        return Promise.reject(new Error('会话ID不能为空'))
    }
    return service.get(`/psychological-chat/sessions/${id}/messages`)
}

export function getDataAnalyticsOverview() {
    return service.get('/data-analytics/overview')
}



