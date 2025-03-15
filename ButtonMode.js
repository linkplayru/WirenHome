var ButtonMode = function(itemName) {
  /*UI*/
  var cellsObject = {};
  cellsObject["protect"] = {
    title: {en: 'protect', ru: 'Только двойное нажатие'},
    type: "switch",
    value: false,
    readonly: false,
    order: 1
  }
  cellsObject["off"] = {
    title: {en: 'off', ru: 'Отключить выключатели'},
    type: "switch",
    value: false,
    readonly: false,
    order: 2
  }
  cellsObject["status"] = {
    title: {en: 'status', ru: 'Режим работы'},
    type: "range",
	value: 0,
	min: 0,
	max: 2,
	readonly: true,
	order: 3
  }
  defineVirtualDevice(itemName, {
    title: {en: itemName, ru: 'Режим работы выключателей'},
    cells: cellsObject
  });

  defineRule({
    whenChanged: ["{}/protect".format(itemName), "{}/off".format(itemName)],
    then: function(value, device, param) {
      if (value) {
        if (param == "protect") {
          getDevice(itemName).getControl("off").setValue({value:false, notify:false});
          dev[itemName]["status"] = 1;
        } else if (param == "off") {
          getDevice(itemName).getControl("protect").setValue({value:false, notify:false});
          dev[itemName]["status"] = 2;
        }
      } else {
        dev[itemName]["status"] = 0;
      }
    }
  });
}

new ButtonMode("ButtonMode");