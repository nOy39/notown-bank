export default {
    state: {
        user: '',
        roles: '',
        data: '',
        manager: '',
        client: '',
        token: ''
    },
    mutations: {
        SET_USER(state, payload) {
            state.user = payload
        },
        SET_ROLES(state, payload) {
            state.roles = payload
        },
        SET_DATA(state, payload) {
            state.data = payload
        },
        SET_MANAGER(state, payload) {
            state.manager = payload
        },
        SET_CLIENT(state, payload) {
            state.client = payload
        },
        SET_TOKEN(state, payload) {
            state.token = payload
        }
    },
    getters: {
        getUser(state) {
            return state.user
        },
        getRoles(state) {
            return state.roles
        },
        getClient(state) {
            return state.client
        },
        getManager(state) {
            return state.manager
        }
    },
    actions: {

    }
}

