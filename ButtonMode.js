var ButtonMode = function(itemName) {
  /*UI*/
  var cellsObject = {};
  cellsObject["normal"] = {
      title: "Нормальный режим",
      type: "switch",
      value: false,
      readonly: false,
      order: 1
  }
  cellsObject["protect"] = {
    title: "Двойной режим",
    type: "switch",
    value: false,
    readonly: false,
    order: 2
  }
  cellsObject["off"] = {
    title: "Режим блокировки",
    type: "switch",
    value: false,
    readonly: false,
    order: 3
  }
  cellsObject["status"] = {
    title: "Режим работы",
    type: "range",
	value: 0,
	min: 0,
	max: 2,
	readonly: true,
	order: 4
  }
  defineVirtualDevice(itemName, {
    title: {en: itemName, ru: 'Режим работы выключателей'},
    cells: cellsObject
  });

  defineRule({
    whenChanged: ["{}/normal".format(itemName), "{}/protect".format(itemName), "{}/off".format(itemName)],
    then: function(value, device, param) {
      if (value) {
        if (param == "normal") {
          getDevice(itemName).getControl("protect").setValue({value:false, notify:false});
          getDevice(itemName).getControl("off").setValue({value:false, notify:false});
          dev[itemName]["status"] = 0;
        } else if (param == "protect") {
          getDevice(itemName).getControl("normal").setValue({value:false, notify:false});
          getDevice(itemName).getControl("off").setValue({value:false, notify:false});
          dev[itemName]["status"] = 1;
        } else if (param == "off") {
          getDevice(itemName).getControl("normal").setValue({value:false, notify:false});
          getDevice(itemName).getControl("protect").setValue({value:false, notify:false});
          dev[itemName]["status"] = 2;
        }
      } else {
        getDevice(itemName).getControl("normal").setValue({value:true, notify:false});
        dev[itemName]["status"] = 0;
      }
    }
  });

  //init
  getDevice(itemName).getControl("normal").setValue({value:true, notify:false});
  getDevice(itemName).getControl("protect").setValue({value:false, notify:false});
  getDevice(itemName).getControl("off").setValue({value:false, notify:false});
  dev[itemName]["status"] = 0;
}

new ButtonMode("ButtonMode");