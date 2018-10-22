import {AXIOS} from "../http-common";

export default {
    state: {
        user: '',
        roles: '',
        auth: false,
        data: '',
        manager: '',
        client: ''
    },
    mutations: {
        SET_USER(state, payload) {
            state.user = payload
        },
        SET_ROLES(state, payload) {
            state.roles = payload
        },
        SET_AUTH(state, payload) {
            state.auth = payload
        },
        SET_DATA(state, payload) {
            state.data = payload
        },
        SET_MANAGER(state, payload) {
            state.manager = payload
        },
        SET_CLIENT(state, payload) {
            state.client = payload
        }
    },
    getters: {
        getUser(state) {
            return state.user
        },
        getAuth(state) {
            return state.auth
        },
        getRoles(state) {
            return state.roles
        }
    },
    actions: {
        setUser({commit, state}, login) {
            commit('SET_ERROR', null)
            commit('IS_LOADING', true)
            AXIOS({
                method: 'post',
                url: '/auth/signin',
                data: {
                    usernameOrEmail: login.usernameOrEmail,
                    password: login.password
                }
            })
                .then(response => {
                    commit('SET_USER', response.data)
                    commit('SET_ROLES', response.data.roles)
                    commit('SET_AUTH', true)
                    commit('IS_LOADING', false)
                    sessionStorage.setItem('token', 'Bearer ' + response.data.accessToken)
                    localStorage.setItem('token', 'Bearer ' + response.data.accessToken)
                })
                .catch(e => {
                    commit('SET_ERROR', e.message)
                    commit('IS_LOADING', false)
                    console.log(e.message.code)
                })
        },
        setAuth({commit, state}, payload) {
            commit('SET_DATA', payload)
            commit('SET_USER', payload.data.user)
            commit('SET_ROLES', payload.data.primaryRole)
            if (payload.data.primaryRole === 'ROLE_MANAGER') {
                commit('SET_MANAGER', payload.data.manager)
            } else if (payload.data.primaryRole === 'ROLE_CLIENT') {
                commit('SET_CLIENT', payload.data.client)
            }
            commit('SET_AUTH', true)

        }
    }
}

