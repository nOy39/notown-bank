import {AXIOS} from "../http-common";

export default {
    state: {
        auth: ''
    },
    mutations: {
        SET_AUTH(state, payload) {
            state.auth = payload
        },
        CLEAR_AUTH(state) {
            state.auth = null
        }
    },
    getters: {
        getAuth(state) {
            return state.auth
        }
    },
    actions: {
        /**
         * Авторизация
         * @param commit
         * @param state
         * @param payload
         */
        setAuth({commit, state}, payload) {
            commit('CLEAR_AUTH')
            commit('SET_ERROR', null)
            AXIOS({
                method: 'post',
                url: '/auth/signin',
                data: payload
            })
                .then(response => {
                    console.log(response.data)
                    commit('SET_USER', response.data.user)
                    commit('SET_TOKEN', response.data.accessToken)
                    if (response.data.client) {
                        commit('SET_CLIENT', response.data.client)
                        commit('SET_CLIENT_ACCOUNTS', response.data.accounts)
                    } else if (response.data.manager) {
                        commit('SET_MANAGER', response.data.manager)
                    }
                    commit('SET_AUTH', true)
                    commit('IS_LOADING', false)
                    return true
                })
                .catch(e => {
                    console.log(e.response)
                    if (e.response.data) {
                        commit('SET_ERROR', e.response.data.message)
                    } else {
                        commit('SET_ERROR', e.message)
                    }
                    commit('IS_LOADING', false)
                })
        },
        /**
         * Выход из авторизированной сессии все авторизированные поля устанавливаются в NULL
         * @param commit
         */
        clearAuth({commit}) {
            commit('SET_AUTH', false)
            commit('SET_CLIENT', null)
            commit('SET_MANAGER', null)
            commit('SET_TOKEN', null)
            commit('SET_USER', null)
            commit('SET_ROLES', null)
            commit('SET_DATA', null)
            commit('SET_CLIENT_ACCOUNTS', null)
        }
    }
}
