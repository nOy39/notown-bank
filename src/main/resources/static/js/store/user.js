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
        setAuth({commit, state}, payload) {
            commit('SET_DATA', payload)
            commit('SET_USER', payload.data.user)
            if (payload.data.primaryRole === 'ROLE_MANAGER') {
                commit('SET_MANAGER', payload.data.manager)
                commit('SET_ROLES', '/manager')
            } else if (payload.data.primaryRole === 'ROLE_CLIENT') {
                commit('SET_CLIENT', payload.data.client)
                commit('SET_ROLES', '/account')
            } else {
                commit('SET_ROLES', '/welcome')
            }
            commit('SET_AUTH', true)
        },
        clearAuth({commit}) {
            commit('SET_AUTH', false)
        }
    }
}

