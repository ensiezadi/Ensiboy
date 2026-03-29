import { defineStore } from "pinia";

const SIDEBAR_MODE_KEY = "ai-vue:sidebar-mode";
const VALID_MODES = ["expanded", "collapsed", "hidden"];

const readSavedMode = () => {
    if (typeof window === "undefined") {
        return "expanded";
    }

    const saved = window.localStorage.getItem(SIDEBAR_MODE_KEY);
    return VALID_MODES.includes(saved) ? saved : "expanded";
};

const persistMode = (mode) => {
    if (typeof window === "undefined") {
        return;
    }
    window.localStorage.setItem(SIDEBAR_MODE_KEY, mode);
};

export const useSidebarStore = defineStore("sidebar", {
    state: () => ({
        mode: readSavedMode(), // expanded | collapsed | hidden
    }),

    getters: {
        isHidden: (state) => state.mode === "hidden",
        isCollapsed: (state) => state.mode === "collapsed",
    },

    actions: {
        setMode(mode) {
            if (!VALID_MODES.includes(mode)) {
                return;
            }
            this.mode = mode;
            persistMode(this.mode);
        },

        cycleMode() {
            if (this.mode === "expanded") {
                this.setMode("collapsed");
                return;
            }

            if (this.mode === "collapsed") {
                this.setMode("hidden");
                return;
            }

            this.setMode("expanded");
        },

        toggleCollapsedMode() {
            if (this.mode === "collapsed") {
                this.setMode("expanded");
                return;
            }

            this.setMode("collapsed");
        },
    },
});
