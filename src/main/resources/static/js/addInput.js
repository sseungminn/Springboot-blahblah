var GUID = function() {
        function e() {
            do var t = "xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx".replace(/[xy]/g, function(e) {
                var t = 16 * Math.random() | 0,
                    i = "x" == e ? t : 3 & t | 8;
                return i.toString(16)
            }); while (!e.register(t));
            return t
        }
        return e.create = function() {
            return e()
        }, e.version = "1.2.0", e.list = [], e.exists = function(t) {
            return e.list.indexOf(t) > -1
        }, e.register = function(t) {
            return e.exists(t) ? !1 : (e.list.push(t), !0)
        }, e
    }(),
    Obj = function() {
        function toFunc(e) {
            if ("function" == typeof e) return e;
            if ("string" == typeof e) {
                if (void 0 != window[e] && "function" == typeof window[e]) return window[e];
                try {
                    return new Function(e)
                } catch (t) {}
            }
            return function() {
                return e
            }
        }

        function Obj() {
            this._guid = GUID(), Object.defineProperty(this, "guid", {
                get: function() {
                    return this._guid
                },
                set: function() {}
            }), this._handlers = [], this.on = function(e, t, i) {
                for (var r = "all", n = null, s = 0, a = 0; a < arguments.length; a++) "string" == typeof arguments[a] ? r = arguments[a].toLowerCase().split(" ") : arguments[a] instanceof Array ? r = $.map(arguments[a], function(e, t) {
                    return e.toLowerCase()
                }) : "function" == typeof arguments[a] ? n = arguments[a] : "number" == typeof arguments[a] && (s = arguments[a]);
                if (!n) return this;
                for (var a = 0; a < r.length; a++) this._handlers.push({
                    event: r[a],
                    handler: n,
                    max_count: s,
                    trigger_count: 0
                });
                return this
            }, this.off = function(e, t) {
                if (void 0 === t && "function" == typeof e)
                    for (var t = e, i = 0; i < this._handlers.length; i++) this._handlers[i].handler == t && this._handlers.splice(i--, 1);
                else if (void 0 === t && "string" == typeof e) {
                    e = e.toLowerCase().split(" ");
                    for (var i = 0; i < this._handlers.length; i++) e.indexOf(this._handlers[i].event) > -1 && this._handlers.splice(i--, 1)
                } else {
                    e = e.toLowerCase().split(" ");
                    for (var i = 0; i < this._handlers.length; i++) e.indexOf(this._handlers[i].event) > -1 && this._handlers[i].handler == t && this._handlers.splice(i--, 1)
                }
                return this
            }, this.trigger = function(e, t) {
                e = e.toLowerCase().split(" ");
                for (var i = 0; i < this._handlers.length; i++)(e.indexOf(this._handlers[i].event) > -1 || "all" == this._handlers[i].event) && toFunc(this._handlers[i].handler).call(this, "all" != this._handlers[i].event ? this._handlers[i].event : e.join(" "), t);
                return this
            }, this._elements = $(), this.renderer = function() {
                var e = $("<div class='Obj'></div>");
                for (var t in this) 0 == t.indexOf("_") && "function" != typeof this[t] && -1 == ["_handlers", "_elements", "_guid"].indexOf(t) && e.append("<div class='Obj-member'><div class='Obj-member-key'>" + t.substr(1) + "</div><div class='Obj-member-value'>" + this[t] + "</div></div>");
                return e
            }, this.refresher = function(e) {
                return this.renderer.apply(this)
            }, this.destroyer = function(e) {}, this.render = function(e, t) {
                var i = this;
                if (void 0 === e) var e = "body";
                if (void 0 === t) var t = "append";
                else t = t.toLowerCase();
                var r = [].slice.call(arguments, 2),
                    n = this;
                return $(e).each(function(e, s) {
                    s = $(s);
                    var a = $(i.renderer.apply(i, r));
                    a.attr("guid", i.guid), i._elements = i._elements.add(a), "append" == t ? s.append(a) : "prepend" == t ? s.prepend(a) : "after" == t ? s.after(a) : "before" == t ? s.before(a) : "return" == t ? n = a : "replace" == t && (s.after(a), s.remove())
                }), this.trigger("render"), n
            }, this.refresh = function(e) {
                for (var t = $(), i = 0; i < this._elements.length; i++) {
                    var r = this._elements.eq(i),
                        n = this.refresher.call(this, r, e);
                    n ? (n.attr("guid", this.guid), this._elements = this._elements.not(r), r.after(n), r.remove(), t = t.add(n)) : t = t.add(r)
                }
                return this._elements = t, this
            }, this.destroy = function() {
                var e = this;
                return this._elements.each(function(t, i) {
                    var r = $(i);
                    r.off(), r.find("*").off(), e.destroyer.call(e, r)
                }), this._elements.remove(), this._elements = $(), delete Obj.directory[this.guid], this
            }, this.defMember = function(e, t, i, r) {
                for (var n = this, s = ["handlers", "on", "off", "trigger", "elements", "render", "renderer", "refresh", "refresher", "destroy", "destroyer", "defMember", "defSettings", "defMethod", "guid"], a = 0; s > a; a++)
                    if (s[a] == e || "_" + s[a] == e) return !1;
                this["_" + e] = void 0 === t ? null : t, Object.defineProperty(this, e, {
                    get: function() {
                        var t = this["_" + e];
                        return r && (t = r.call(n, t)), this.trigger("get" + e + " " + e, t), t
                    },
                    set: function(t) {
                        if (i) {
                            var r = i.call(n, t);
                            void 0 !== r && (t = r)
                        }
                        this["_" + e] = t, this.trigger("set" + e + " " + e, t), this.refresh(e)
                    }
                })
            }, this.defSettings = function(e) {
                if (void 0 === e) var e = {};
                this._settings = e, Object.defineProperty(this, "settings", {
                    get: function() {
                        return this.trigger("getsettings settings", this._settings), this._settings
                    },
                    set: function(e) {
                        this._settings = $.extend(this._settings, e), this.trigger("setsettings settings", this._settings), this.refresh("settings")
                    }
                })
            }, this.defMethod = function(e, t) {
                var i = this;
                this["_" + e] = t, this[e] = function() {
                    var t = i["_" + e].apply(i, arguments);
                    return i.trigger(e, arguments), void 0 != t ? t : i
                }
            }, Obj.directory[this.guid] = this
        }
        return Obj.version = "2.1.2", Obj.directory = {}, Obj.extend = function(e, t) {
            t || (t = Obj);
            var i = function() {
                t.apply(this, arguments), e.apply(this, arguments)
            };
            return e.prototypoe = Object.create(t.prototype), i.prototype = Object.create(e.prototype), i
        }, Obj.create = function(o) {
            function Proto() {
                Obj.apply(this)
            }
            if ("function" == typeof o) return Obj.extend(o);
            if ("object" == typeof o) {
                var cc = "function Proto(){Obj.apply(this);";
                for (var k in o) {
                    var v = o[k];
                    "function" == typeof v ? cc += ["init", "renderer", "refresher", "destroyer"].indexOf(k) > -1 ? "this." + k + "=" + v + ";" : "this.defMethod('" + k + "'," + v + ");" : ("string" == typeof v && (v = '"' + v + '"'), cc += "this.defMember('" + k + "', " + v + ");")
                }
                return o.init && (cc += "this.init.apply(this,arguments);"), cc += "};Proto.prototype = Object.create(Obj.prototype);", eval(cc), Proto
            }
            return Proto.prototype = Object.create(Obj.prototype), Proto
        }, Obj
    }();
if (typeof($add) == "undefined") var $add = {
    version: {},
    auto: {
        disabled: false
    }
};
! function(t) {
    $add.version.Input = "1.1.1", $add.Input = function(e, a) {
        var i = t(e).map(function(e, i) {
            var d = t(i),
                n = t.extend({}, d.data(), a);
            d.attr("name") && (n.name = d.attr("name")), d.attr("id") && (n.id = d.attr("id")), d.attr("placeholder") && (n.placeholder = d.attr("placeholder")), d.attr("type") && (n.type = d.attr("type")), d.val().trim().length && (n.value = d.val());
            var u = new $add.Input.obj(n.id, n.name, n.type, n.placeholder, n.value);
            return u.render(d, "replace"), u
        });
        return 0 == i.length ? null : 1 == i.length ? i[0] : i
    }, $add.Input.obj = Obj.create(function(e, a, i, d, n) {
        this.defMember("id"), this.defMember("name"), this.defMember("type", "text"), this.defMember("placeholder", ""), this.defMember("value", ""), this.renderer = function() {
            var e = this,
                a = t("<div class='addui-input-container'></div>"),
                i = t("<input type='" + this.type + "' name='" + this.name + "' id='" + (this.id || this.guid) + "' class='addui-input-input' />").on("change", function(t) {
                    e.value = i.val()
                }).appendTo(a);
            return t("<div for='" + (this.id || this.guid) + "' class='addui-input-placeholder'>" + this.placeholder + "</div>").appendTo(a), this.value.trim().length > 0 && (a.addClass("addui-input-hasValue"), i.val(this.value)), a
        }, this.refresher = function(t, e) {
            "placeholder" == e ? t.find(".addui-input-placeholder").html(this.placeholder) : "type" == e ? t.find(".addui-input-input").attr("type", this.type) : "value" == e && (this.value.trim().length > 0 ? t.addClass("addui-input-hasValue") : t.removeClass("addui-input-hasValue"))
        }, this.init = function(t, e, a, i, d) {
            t && (this.id = t), e && (this.name = e), a && (this.type = a), i && (this.placeholder = i), d && (this.value = d)
        }, this.init.apply(this, arguments)
    }), t.fn.addInput = function(t) {
        $add.Input(this, t)
    }, $add.auto.Input = function() {
        $add.auto.disabled || t("[data-addui=input]").addInput()
    }
}(jQuery);
$(function() {
    for (var k in $add.auto) {
        if (typeof($add.auto[k]) == "function") {
            $add.auto[k]();
        }
    }
});